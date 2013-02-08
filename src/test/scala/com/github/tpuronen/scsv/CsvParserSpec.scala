package com.github.tpuronen.scsv

import org.scalatest.FunSpec
import CsvParser._

class CsvParserSpec extends FunSpec {
  val multilineInput =
    """id1,val1,val12
      |id2,val2,val22
      |id3,val3,val32
      |""".stripMargin
  val multilineInvalidInput =
    """id1,val1,val12
      |id2,val2,val22, extra
      |id3,val3,val32
      |""".stripMargin

  describe("CSV parser") {
    it("should return empty list of String lists with empty input") {
      val result = CsvParser("")
      assert(result.successful === true)
      assert(result.get.isEmpty === true)
    }

    it("should return element with whitespace only input") {
      val result = CsvParser("\t  \t  ")
      assert(result.successful === true)
      assert(result.get.length === 1)
      assert(result.get(0).length === 1)
    }

    it("should return list with tuple with one line") {
      val result = CsvParser("""id1,value,2ndvalue""")
      assert(result.successful === true)
      assert(result.get.length === 1)
      assert(result.get.head.length === 3)
      assert(result.get.head(0) === "id1")
    }

    it ("should return list of tuples with many lines") {
      val result = CsvParser(multilineInput)
      assert(result.successful === true)
      assert(result.get.length === 3)
      assert(result.get(0).head === "id1")
    }

    it ("should preserve white spaces") {
      val result = CsvParser("  id,some value  , another field with   spaces  ")
      assert(result.successful === true)
      assert(result.get.length === 1)
      assert(result.get(0).length === 3)
      assert(result.get(0)(0) === "  id")
      assert(result.get(0)(1) === "some value  ")
      assert(result.get(0)(2) === " another field with   spaces  ")
    }

    it ("should parse quoted attributes") {
      val result = CsvParser("""id1,"quoted value, with comma",val2""")
      assert(result.successful === true)
      assert(result.get.length === 1)
      assert(result.get(0).length === 3)
      assert(result.get(0)(1) === "quoted value, with comma")
    }

    it ("should parse quoted attributes with double quotes") {
      val result = CsvParser("""id1,"value, contains ""double quotes"" too",val2""")
      assert(result.successful === true)
      assert(result.get.length === 1)
      assert(result.get(0).length === 3)
      assert(result.get(0)(1) === """value, contains "double quotes" too""")
    }

    it ("should validate data if asked") {
      assert(validate(CsvParser(multilineInput)).successful === true)
      val result = validate(CsvParser(multilineInvalidInput))
      assert(result.successful === false)
      val message = result match {
        case NoSuccess(msg, _) => msg
        case _ => ""
      }
      assert(message === "Record lengths do not match")
    }
  }
}
