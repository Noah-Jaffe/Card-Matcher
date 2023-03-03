local CardGameReader = require("CardGameAnalyzer")

-- file of card game output -- 
local fileName = "proj_output.txt"

---------------------------------------
--               MAIN                --
---------------------------------------

local readSuccess = readFile(fileName)

--if there was an error reading the file, quit this program
if readSuccess == true then
  local gameStats = getStats()

  for i = 1, #gameStats, 1 do
    print(gameStats[i])
  end
else
  return
end