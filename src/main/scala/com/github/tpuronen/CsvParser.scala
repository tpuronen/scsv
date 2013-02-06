package com.github.tpuronen

import util.parsing.combinator._

object CsvParser extends RegexParsers {
  override val skipWhitespace = false

  val doubleQuote = "\""
  val twoDoubleQuotes = "\"\"" ^^ {case _ => "\""}
  val comma = ","
  val lineFeed = "\r?\n".r
  def plainChar = "[^\",\n\r]+".r
  def element: Parser[String] = plain|quoted
  def plain: Parser[String] = (plainChar) ^^ { case cs => cs.mkString("")}
  def quoted:  Parser[String] = doubleQuote ~> ((plainChar|twoDoubleQuotes|comma|lineFeed)*) <~ doubleQuote ^^ {
    case cs => cs.mkString("")
  }
  def line: Parser[List[String]] = rep1sep(element, ",")
  def document: Parser[List[List[String]]] = repsep(line, lineFeed) <~ (lineFeed?)

  def apply(input: String): List[List[String]] = parseAll(document, input) match {
    case Success(result, _) => result
    case failure: NoSuccess => {println(failure);List[List[String]]()}
  }

}
