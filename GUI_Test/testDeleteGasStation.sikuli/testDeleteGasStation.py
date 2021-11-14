#test delete a gas station
click("1590739812672.png")
type(Pattern("1590769442291.png").exact(),"password")
type(Pattern("1590769472977.png").exact(),"admin@ezgas.com")
click("1590742463496.png")
click(Pattern("1590745954362.png").exact())
wait("1590832325042.png")
type(Key.PAGE_DOWN)
click(Pattern("1593505586393.png").targetOffset(640,0))