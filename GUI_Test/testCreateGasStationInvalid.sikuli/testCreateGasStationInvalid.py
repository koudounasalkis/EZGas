#Test create a gas station
click("1590739812672.png")
type(Pattern("1590769442291.png").exact(),"password")
type(Pattern("1590769472977.png").exact(),"admin@ezgas.com")
click("1590742463496.png")
click(Pattern("1590745954362.png").exact())
type(Pattern("1590771003646.png").exact(), "TestGasStation2")
type(Pattern("1590771049048-1.png").exact(),"Corso Duca degli Abruzzi Turin Piemont Italy")
wait("list_results.png")
click(Pattern("correct_result.png").exact())

click("1590773324066.png")
type("a", KeyModifier.CTRL) # select all text
type(Key.BACKSPACE) # delete selection
type("100.0192")
type(Key.PAGE_DOWN)
click("1590771477739.png")
click(Pattern("Screenshot_20200529_185857.png").exact())
click(Pattern("1590771711957.png").similar(0.73).targetOffset(-40,0))
click(Pattern("1590771723499.png").similar(0.81).targetOffset(-38,0))
click("1590772198618.png")