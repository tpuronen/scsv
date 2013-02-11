Simple CSV parser for Scala
===========================


```scala
scala> val result = CsvParser("""Scala,Odersky
                                |Clojure,Hickey
                                |Ioke,Bini""".stripMargin)
result: CsvParser.ParseResult[List[List[String]]] = [3.10] parsed: List(List(Scala, Odersky), List(Clojure, Hickey), List(Ioke, Bini))
```

Validation
----------

The results can be validated to check that all records contain the same number of fields.

```scala
scala> val result = validate(CsvParser("""Scala,Odersky
                                         |Linq,C#,Meijer""".stripMargin))
[2.15] failure: Record lengths do not match

Linq,C#,Meijer
scala> result.successful
false
scala> val result = validate(CsvParser("""Scala,Odersky
                                         |"Linq,C#",Meijer""".stripMargin))
result: CsvParser.ParseResult[List[List[String]]] = [2.17] parsed: List(List(Scala, Odersky), List(Linq,C#, Meijer))
scala> result.get(1)(0)
res5: String = Linq,C#
```

