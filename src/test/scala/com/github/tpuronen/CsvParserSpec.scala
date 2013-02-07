package com.github.tpuronen

import org.scalatest.FunSpec

class CsvParserSpec extends FunSpec {
  describe("CSV parser") {
    it("should return empty list of String lists with empty input") {
      assert(CsvParser("").isEmpty)
    }

    it("should return element with whitespace only input") {
      assert(CsvParser("\t  \t  ").length === 1)
    }

    it("should return list with tuple with one line") {
      val result = CsvParser("""id1,value,2ndvalue""")
      assert(result.length === 1)
      assert(result.head.length === 3)
      assert(result.head(0) === "id1")
    }

    it ("should return list of tuples with many lines") {
      val input =
        """id1,val1,val12
          |id2,val2,val22
          |id3,val3,val32
          |""".stripMargin

      val result = CsvParser(input)
      assert(result.length === 3)
      assert(result(0).head === "id1")
    }

    it ("should preserve white spaces") {
      val result = CsvParser("  id,some value  , another field with   spaces  ")
      println(result)
      assert(result.length === 1)
      assert(result(0).length === 3)
      assert(result(0)(0) === "  id")
      assert(result(0)(1) === "some value  ")
      assert(result(0)(2) === " another field with   spaces  ")
    }

    it ("should parse quoted attributes") {
      val result = CsvParser("""id1,"quoted value, with comma",val2""")
      assert(result.length === 1)
      assert(result(0).length === 3)
      assert(result(0)(1) === "quoted value, with comma")
    }

    it ("should parse quoted attributes with double quotes") {
      val result = CsvParser("""id1,"value, contains ""double quotes"" too",val2""")
      assert(result.length === 1)
      assert(result(0).length === 3)
      assert(result(0)(1) === """value, contains "double quotes" too""")
    }
  }
}
