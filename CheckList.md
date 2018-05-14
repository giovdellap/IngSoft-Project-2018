###TEST CHECKLIST 1.0


Die:
- [x] checkColor
- [X] checkValue
- [X] checkThrow
- [X] checkDisabling
- [X] checkSetValueTest
- [X] checkSetValueTestException

DiceContainer:
- [X] checkNotNull
- [X] checkInvalidThrow
- [X] testThatDice
        //check that throwing 90 dices extracts 18 dice for color
      
RoundDice
- [X] checkAddandGet
- [X] checkAddException
- [X] checkGetException
- [X] checkDelete
- [X] checkDeleteException
- [X] checkReturnDim

RoundTrack
- [X] checkAddandReturnTurn
- [X] checkRoundDiceisReturned
- [X] checkReturnNException
- [X] checkSpecificSet
- [X] checkSpecificSetException

SchemeCard
- [X] checkGetID
- [X] checkDisabledScheme
- [X] checkSetGetName
- [X] checkSetNameException
- [X] checkGetNameException
- [X] checkSetGetDie
- [X] checkSetDieException1
        //invalid argument of type int
- [X] checkSetDieException2
        //invalid argument of type die
- [X] checkSetDieException3
        //invalid position - occupied cell - must use replaceDie
- [X] checkGetDieException
- [X] checkSetGetCell
- [X] checkSetCellException1
        //wrong FrontBack
- [X] checkSetCellException2
        //wrong x
- [X] checkSetCellException3
        //wrong value to insert
- [X] checkSetGetDiff
- [X] checkSetDiffException1
        //invalid FrontBack
- [X] checkSetDiffException2
        //invalid diff
- [X] checkGetDiffException
- [X] checkSetGetFb
- [X] checkSetFbException
- [X] checkGetFront
- [X] checkGetBack
- [X] checkReplaceDie
- [X] checkReplaceDieException1
        //invalid argument of type Die
- [X] checkReplaceDieException2
        //invalid argument of type Die
- [X] checkReplaceDieException2

SchemesDeck
- [X] checkForDifferentID
        //checks that extracted schemes are all different
- [X] checkExtractSchemesException
- [X] checkExtractSchemesID
- [X] checkExtractSchemesIDException

DraftPool
- [X] checkReturnDie
- [X] checkReturnException
- [X] checkPickUpDie
- [X] checkPickUpException
- [X] checkupdateDraftDice
- [X] checkReplaceDie
- [X] checkReplaceDieException1
- [X] checkReplaceDieException2
- [X] checkReplaceDieException3
- [X] checkReplaceDieException4

PrivateObjective
- [X] checkGetColor
- [X] checkCalculateBonus

PrivateObjectivesdeck
- [X] checkExtract
- [X] checkExtractException

PublicObjectives
- [X] checkGetID
- [X] checkInvalidIntArgumentException
- [X] checkGetBonus
- [X] checkDisablePublicObjective
- [X] checkToString

PublicObjectiveCalculationTest
- [X] checkCalculateOne
- [X] checkCalculateTwo
- [X] checkCalculateThree
- [X] checkCalculateFour
- [X] checkCalculateFive
- [X] checkCalculateSix
- [X] checkCalculateSeven
- [X] checkCalculateEight
- [X] checkCalculateNine
- [X] checkCalculateTen

Planning to extend branch coverage by new extended parameters(checks should be right, aren't them?)

