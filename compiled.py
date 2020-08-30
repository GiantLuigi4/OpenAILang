from com.tfc.openAI.lang.utils import InputList
from java.util import Random

rand = Random()
getPixel = % display %
aiInstance = % id %


def keyPress(key):
    InputList.add(aiInstance, 'key:' + str(key))


def click(key):
    InputList.add(aiInstance, 'click:' + str(key))


def mouseMove(key):
    InputList.add(aiInstance, 'mouseMove:' + str(key))


def AI():
    color = 16777215
    randVal = rand.nextInt(10) + 0
    print(randVal)
    if getPixel[0][0] == color:
        keyPress('w')
    else:
        keyPress('s')
    click('left')
    mouseMove('0,0')
AI()
