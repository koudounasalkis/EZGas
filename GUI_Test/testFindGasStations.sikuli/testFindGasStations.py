#Test search gas station in an area
type("1590833510260.png","Corso Duca degli Abruzzi Turin Piemont Italy")
wait("list_results.png")
click(Pattern("correct_result.png").similar(0.85))
type("1592126242136.png", "3")
click("1590833774774.png")
type(Key.PAGE_DOWN)
wait("1593506892885.png")
