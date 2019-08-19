package quantum.gates

sealed abstract class Symbol(val symbol: Char)
case object NOT extends Symbol('◯')
case object CONTROL extends Symbol('●')
case object EMPTY extends Symbol('-')
case object SWAP extends Symbol('x')
case object HADAMARD extends Symbol('H')