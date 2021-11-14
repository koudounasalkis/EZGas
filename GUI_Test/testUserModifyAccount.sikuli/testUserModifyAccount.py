#Test to modify an user name
click("1590742382013.png")
type(Pattern("1590742441864.png").exact(),"password")
type(Pattern("1590742843255.png").exact(),"test@gui.it")
click("1590742463496.png")
click("1590743263094.png")
click(Pattern("1590743311075.png").exact())
type("a", KeyModifier.CTRL) # select all text
type(Key.BACKSPACE) # delete selection
type("UserTester")
type("1590743357439.png","password")
click("1590743382760.png")