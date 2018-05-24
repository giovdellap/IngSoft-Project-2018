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
        //check that throwing 90 dices extracts 18 dice per color
      
CheckingMethods:
- [X] checkMoveOneFalse
- [X] checkMoveOneTrue
- [X] checkMoveTwoFalse
- [X] checkMoveTwoTrue
- [X] checkMoveThreeFalse
- [X] checkMoveThreeTrue
- [X] checkMoveFourTrue
- [X] checkMoveFourFalse
- [X] checkMoveFiveTrue
- [X] checkMoveFiveFalse
- [X] checkFirstMoveOne
- [X] checkFirstMoveTwo
- [X] checkFirstMoveThree
- [X] checkFirstMoveFour
- [X] checkFirstMoveFive

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

MajorLogger
- [X] checkLogandReturnLog
- [X] checkLogException
- [X] checkGetLog
- [X] checkStackLogException
- [X] checkToString

MinorLogger
- [X] checkLogandUpdateFather
- [X] checkLogException
- [X] checkStackLog
- [X] checkStackLogException
- [X] checkReinitialize

PublicObjectiveCalculationTest
- [X] checkCalculateOne
- [X] checkCalculateOneException
- [X] checkCalculateTwo
- [X] checkCalculateTwoException
- [X] checkCalculateThree
- [X] checkCalculateThreeException
- [X] checkCalculateFour
- [X] checkCalculateFourException
- [X] checkCalculateFive
- [X] checkCalculateFiveException
- [X] checkCalculateSix
- [X] checkCalculateSixException
- [X] checkCalculateSeven
- [X] checkCalculateSevenException
- [X] checkCalculateEight
- [X] checkCalculateEightException
- [X] checkCalculateNine
- [X] checkCalculateNineException
- [X] checkCalculateTen
- [X] checkCalculateTenException

ToolCardOne
- [X] checkDieOneToSix
- [X] checkDieSixToOne
- [X] checkFullCellException
- [X] checkApplyModifies


Planning to extend branch coverage by new extended parameters(checks should be right, aren't them?)

