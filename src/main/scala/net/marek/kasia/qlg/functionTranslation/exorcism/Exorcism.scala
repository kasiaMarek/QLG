package net.marek.kasia.qlg.functionTranslation.exorcism

import net.marek.kasia.qlg.functionTranslation.{ESOP, Term}

object Exorcism {

  def exorcism(esop: ESOP): ESOP = ESOP(exor3Loop(esop.terms),esop.variables)

  def exor3Loop(termsIn: List[Term]): List[Term] =
   exorLoop(termsIn, exor2Loop, 3)

  def exor2Loop(termsIn: List[Term]): List[Term] =
    exorLoop(termsIn, exor1Loop, 2)

  def exorLoop(termsIn: List[Term], f: List[Term] => List[Term], diff: Int): List[Term] = {
    var costPrev : Int = countCost(termsIn)
    var costNow : Int = costPrev
    var terms = f(termsIn)
    var solution = terms
    costNow = countCost(terms)

    do {
      costPrev = costNow
      solution = terms

      val p = performExorlink(terms, diff)
      terms = p._1

      terms = f(terms)

      costNow = countCost(terms)
    } while(costNow < costPrev)

    solution
  }

  def exor1Loop(termIn: List[Term]): List[Term] = {
    var terms = termIn
    var changed: Boolean = true
    do {
      val p = performExorlink(terms, 1)
      changed = p._2
      terms = p._1
    } while (changed)
    terms
  }

  def performExorlink(terms: List[Term], diff: Int): (List[Term], Boolean) = {
    var all = terms.distinct.sorted
    var left = all
    var newTerms: List[(Term, Term)] = List()

    while(left.nonEmpty) {
      val n = left.head
      left = left.tail

      var mergeWith = left
      while(mergeWith.nonEmpty) {
        val e = mergeWith.head
        mergeWith = mergeWith.tail
        if(e.difference(n) == diff) {
          left = left.filterNot(_ == n)
          newTerms = (e, n) :: newTerms
          mergeWith = List()
        }
      }

    }

    (all.filterNot(t => newTerms.exists(p => p._1 == t || p._2 == t)) ::: newTerms.flatMap(p => exorlink(p._1, p._2)),
      newTerms.nonEmpty)
  }

  private def countCost(terms: List[Term]): Int = terms.size

  def exorlink(term1: Term, term2: Term): List[Term] = {
    val indices =
      (for(i <- 0 until term1.numOfVariables) yield if(term1(i) != term2(i)) i else -1).filterNot(_ == -1)

    (for(diff <- indices) yield Term((
      for(i <- 0 until term1.numOfVariables) yield
        if(i < diff) term1(i)
        else if(i > diff) term2(i)
        else 2).toList)
      ).toList
  }

}
