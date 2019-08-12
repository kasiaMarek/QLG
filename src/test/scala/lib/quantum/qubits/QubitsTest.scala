package lib.quantum.qubits

import breeze.linalg.DenseVector
import breeze.numerics.sqrt
import org.scalatest.FunSuite


class QubitsTest extends FunSuite {
  test("tensor") {
    assert(Qubits.tensor(DenseVector(1.0, 2.0, 0.5), DenseVector(1.0, 0.5)) == DenseVector(1.0, 0.5, 2.0, 1.0, 0.5, 0.25))
  }

  test("make qbits from list") {
    assert(new Qubits(1,0,0,1).q == DenseVector(
      0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0
    ))
  }

  test("bin to dec") {
    assert(Qubits.binToDec(List(1,0,1,0)) == 10)
  }

//  test("dec to bin") {
//    assert(Qubits.decToBin(10) == List(1,0,1,0))
//  }

  test("measure probability") {
    //TODO:: eq for double
//    assert(
//      new Qubits(
//        new DenseVector(
//          Array(0.5, 1/sqrt(2), 0, 0.5)
//        ), 2
//      ).getProbabilitiesForAQubit(1) == (0.25, 0.75)
//    )
  }


}
