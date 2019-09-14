package quantum
import breeze.math.Semiring
import prefs.Prefs._

case class QNum private(powers: List[Double] = List(0), isZero: Boolean = false, sign: List[Int] = List(1)) {

  def this(power: Double) {
    this(List(power))
  }

  def this(power: Double, sign: Int) {
    this(List(power), sign = List(sign))
  }

  def this(power: Double, isZero: Boolean) {
    this(List(power), isZero)
  }

  def *(q: QNum)(implicit semiring: Semiring[QNum]): QNum = semiring.*(this, q)

  def toDouble(): Double = if(isZero) 0 else sign * powers.fold(0.0)((acc, pow) => acc + Math.pow(2, pow))

//  override def toString: String = if(isZero) "0" else if(pow == 0) "1" else if(sign == -1) "pow: -" + pow else "pow: " + pow
}

class QNumSemiring extends Semiring[QNum] {
  override def +(a: QNum, b: QNum): QNum = {
   if(QNum.zero == a) b else if(QNum.zero == b) a else QNum(a.powers ::: b.powers)
  }

  override def *(a: QNum, b: QNum): QNum = {
    if (a.isZero || b.isZero) {
      QNum.zero
    }
    else {
      QNum(a.powers.flatMap( apow => b.powers.map(bpow => apow * bpow)), sign = a.sign*b.sign)
    }
  }

  override def ==(a: QNum, b: QNum): Boolean = a == b

  override def !=(a: QNum, b: QNum): Boolean = a != b

  override def zero: QNum = QNum.zero

  override def one: QNum = QNum.one
}

object QNum {
  def zero: QNum = new QNum(0, true)
  def one: QNum = new QNum(0)
  def `-one`: QNum = new QNum(0, sign = -1)
  def `1/sqrt2`: QNum = new QNum(-0.5)
}
