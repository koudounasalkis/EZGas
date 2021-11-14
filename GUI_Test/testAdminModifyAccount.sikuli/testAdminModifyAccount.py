#Test to modify an user through admin panel
click("1590739812672.png")
type(Pattern("1590769442291.png").exact(),"password")
type(Pattern("1590769472977.png").exact(),"admin@ezgas.com")
click("1590742463496.png")
click(Pattern("1590745954362.png").exact())
click(Pattern("1590746396009.png").similar(0.90).targetOffset(500,0))
click(Pattern("1590746622888.png").exact())
type("a", KeyModifier.CTRL) # select all text
type(Key.BACKSPACE) # delete selection
type("Usertester")
click("1590746725099.png")