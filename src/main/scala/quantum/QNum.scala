package quantum
import breeze.math.Semiring
import breeze.storage.Zero

case class QNum (rationalPart: Double = 0, sqrtPart: Double = 0) {
  def toDouble() : Double = rationalPart + sqrtPart/Math.sqrt(2)
  def *(q: QNum) = QNum.*(this, q)
}

object QNum extends Semiring[QNum] with Zero[QNum]{
  override def +(a: QNum, b: QNum): QNum = QNum(a.rationalPart + b.rationalPart, a.sqrtPart + b.sqrtPart)

  override def *(a: QNum, b: QNum): QNum = QNum(
    a.rationalPart*b.rationalPart + a.sqrtPart*b.sqrtPart/2,
    a.rationalPart*b.sqrtPart + b.rationalPart*a.sqrtPart
  )

  override def ==(a: QNum, b: QNum): Boolean = a == b

  override def !=(a: QNum, b: QNum): Boolean = a != b

  override def zero: QNum = QNum()

  override def one: QNum = QNum(rationalPart = 1)

  def `-one`: QNum = QNum(rationalPart = -1)

  def `1/sqrt2`: QNum = QNum(sqrtPart = 1)
}