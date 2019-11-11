package lib

import net.marek.kasia.qlg.FromStringParser
import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks

class FullTest extends FunSuite with TableDrivenPropertyChecks {

    test("simple one value test") {
      forAll(testCases) {
        (_: String, fileName: String, resultName: String, resultValue: Int) => {
          val source = scala.io.Source.fromFile("tests/" + fileName)
          val program = try source.mkString finally source.close()

          val circuitExec = new FromStringParser(program).parse()
          val index = circuitExec.variableNames.indexOf(resultName)
          assert(circuitExec.getResult.measureAllReturnAsListOfBits()(index) == resultValue)
        }
      }
    }

    val testCases = Table(
      ("testCase", "fileName", "resultName", "resultValue"),
      ("copyTest", "copyTest", "b", 1),
      ("simpleOrTest", "simpleOrTest", "c", 1),
      ("function1", "function1", "res", 1),
      ("function2", "function2", "res", 0)
    )
}
