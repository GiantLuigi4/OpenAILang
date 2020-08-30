from com.tfc.openAI.lang.utils import InputList

getPixel = % display %
aiInstance = % id %


def keyPress(key):
    InputList.add(aiInstance, 'key:' + str(key))


def click(key):
    InputList.add(aiInstance, 'click:' + str(key))


def mouseMove(key):
    InputList.add(aiInstance, 'mouseMove:' + str(key))


def AI():
    if getPixel[0][0] == 16777215:
        keyPress('w')
    else:
        keyPress('s')

    click('left')
    mouseMove('0,0')


AI()
