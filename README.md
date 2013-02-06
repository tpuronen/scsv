Simple parser for CSV files.

String a -> List[List[String]]


CsvParser("""Scala,Odersky
   Clojure,Hickey
   Ioke,Bini""")

returns

List(List("Scala","Odersky"), List("Clojure","Hickey"), List("Ioke","Bini"))
