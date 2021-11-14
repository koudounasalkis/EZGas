#Test modify a gas station
click("1590739812672.png")
type(Pattern("1590769442291.png").exact(),"password")
type(Pattern("1590769472977.png").exact(),"admin@ezgas.com")
click("1590742463496.png")
click(Pattern("1590745954362.png").exact())
wait("1590832325042.png") #to wait for the page to be loaded
type(Key.PAGE_DOWN)
click(Pattern("1593505586393-2.png").targetOffset(570,0))
click(Pattern("1590832224197.png").targetOffset(-35,0))
click("1590832391455.png")