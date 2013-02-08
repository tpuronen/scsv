package com.github.tpuronen

import com.github.tpuronen.scsv.CsvParser._

package object scsv {
  def validate(result: ParseResult[List[List[String]]]): ParseResult[List[List[String]]] = {
    if (!result.successful) result

    val length = result.get(0).length
    if (result.get.forall {_.length == length}) result
    else                                        Failure("Record lengths do not match", result.next)
  }
}
