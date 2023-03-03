---------------------------------------
--         GLOBAL VARIABLES          --
---------------------------------------

-- the master list of game statisics --
AllGameInfo = {}

---------------------------------------
--       FUNCTION DECLERATIONS       --
---------------------------------------
---function readFile reads in a file line by line
-- stores each games' statistics into a master table of game stats
function readFile(fileName)
  --set input, create contents of file, create index
  local file = io.open(fileName, "r")
  io.input(file)
  if file == nil then
    print("ERROR readFile(): **FILE NOT FOUND**")
    io.close()
    return false
  end

  local index = 1
  local matches = {Player1 = 0, Player2 = 0}
  local numGuesses = 0
  local biggestStreak = 1
  local currentStreak = 0
  local winners = {}
  local gameNum = 0
  local lastPlayer = ""
  local lastCorrectGuess = 0
  local lastMatchLine = -7
  local lineNum = 1;

  -- at each start game, setup a new table to store game statistics
  -- record number of turns
  -- record number of matches and who got the match
  -- record the longest streak
  -- record the winner(s)
  -- at the end of every game store the current game statistics table into the masterlist

  line = io.read()
  
  repeat
    
    if line:find("START GAME:") then
      gameNum = gameNum + 1

    elseif line:find("'s turn.") then
      --skip over "board after guess" and the printed board
      numGuesses = numGuesses + 1
      lastPlayer = line:sub(1, line:find("'s turn")-1)
      lineNum = lineNum + 5
      for i=1, 5, 1 do
        line = io.read()
      end

    elseif line:find("Congrats!") then
      --find which player matched, if it was the same player as last turn increment biggest count,
      --if it was a different player, reset streak counter
      --add players match count to the game info

      currentPlayer = line:sub(11, line:find(" matched the cards ") - 1)
      if lineNum - 7 == lastMatchLine then
        currentStreak = currentStreak + 1
        if currentStreak > biggestStreak then
          biggestStreak = currentStreak 
        end
      else
        currentStreak = 1
      end
      lastMatchLine = lineNum
      matches[currentPlayer] = matches[currentPlayer] + 1
     
    elseif line:find("WINNER: ") then
      --if theres one winner, add their name to the winners table
      winners = { line:sub(9, line:find(" won")) }

  elseif line:find("TIE: ") then
    --this part was commented out to not count wins as a tie. uncommenting this block will allow for ties to count as a win for all players that tied.
    --[[
      --if there is a tie, add all the player names that tied to the winners table

      local names = line:sub(5, line:find("tied") - 1)
      local splitOn = {}
      splitIndex = 1

      --find where words start and end
      for i = 1, #names, 1 do
        if names:byte(i) == 32 then
          splitOn[splitIndex] = i
          splitIndex = splitIndex + 1
        end
      end

      --find each name
      splitIndex = 0
      for i=1, #splitOn - 1, 2 do
        winners[splitIndex] =  names:sub(splitOn[i] + 1,splitOn[i+1] -1)
        splitIndex = splitIndex + 1
      end
    --]]
    
    end -- end elseifs
    
    if line:find("TIE:") or line:find("WINNER:") then
      --if end of game, reset per game data
      AllGameInfo[gameNum] = {numGuesses, biggestStreak, winners, {matches["Player1"], matches["Player2"]} }
      matches = {Player1 = 0, Player2 = 0}
      numGuesses = 0
      biggestStreak = 1
      currentStreak = 0
      winners = {}
    end -- end reset data block
    
    line = io.read()
    lineNum = lineNum + 1
  until line == nil

  io.close()
  
  --return successful read
  return true
end

---Get the player(s) that won the most and with how many wins
function getMostWon()
  
  local mostWon = {0, 0}
  
  --get the player that won the most
  for i=1, #AllGameInfo do
    for k in pairs(AllGameInfo[i][3]) do
        if AllGameInfo[i][3][k]:find("Player1") then
          mostWon[1] = mostWon[1] + 1
        end
        if AllGameInfo[i][3][k]:find("Player2") then
          mostWon[2] = mostWon[2] + 1
        end
      end
  end
  
  if mostWon[1] > mostWon[2] then
    return "Player1 WON THE MOST GAMES WITH " .. mostWon[1] .. " WINS!"
  elseif mostWon[1] < mostWon[2] then
    return "Player2 WON THE MOST GAMES WITH " .. mostWon[2] .. " WINS!"
  elseif mostWon[1] == mostWon[2] then
    return "PLAYER1 AND PLAYER2 TIED WITH " .. mostWon[1] .. " WINS!"
  end
  
end

---Get the longest streak any player went on, that is consecutive correct guesses in a single turn
function getLongestStreak()
  
  longestStreakIndex = 1
  
  for i=1, #AllGameInfo do
    if AllGameInfo[i][2] > AllGameInfo[longestStreakIndex][2] then
      longestStreakIndex = i
    end
  end

  return "LONGEST STREAK IN GAME# " .. longestStreakIndex .. " WITH " .. AllGameInfo[longestStreakIndex][2] .. " MATCHES IN A ROW!"
  
end

---Get the game that took the most guesses and how many
-- Get the game that took the least guesses and how many
-- Get the average number of guesses per game
function getGuessStats()
  
  local mostGuessIndex = 1; 
  local leastGuessIndex = 1;
  local totalGuesses = 0;

  for i=1, #AllGameInfo do
    totalGuesses = totalGuesses + AllGameInfo[i][1]
    if AllGameInfo[i][1] > AllGameInfo[mostGuessIndex][1] then
      mostGuessIndex = i
    end
    if AllGameInfo[i][1] < AllGameInfo[leastGuessIndex][1] then
      leastGuessIndex = i
    end
  end

  return "MOST GUESSES IN GAME# " .. mostGuessIndex .. " WITH " .. AllGameInfo[mostGuessIndex][1] .. " GUESSES!", "LEAST GUESSES IN GAME# " .. leastGuessIndex .. " WITH " .. AllGameInfo[leastGuessIndex][1] .. " GUESSES!", ("AVERAGE GUESSES PER GAME: " .. (totalGuesses / #AllGameInfo))
end


---Get the average number of matches found by player 1 and player 2
function getAvgMatch()

  local avgMatch = {0, 0}

  for i=1, #AllGameInfo, 1 do
    avgMatch[1] = avgMatch[1] + AllGameInfo[i][4][1]
    avgMatch[2] = avgMatch[2] + AllGameInfo[i][4][2]
  end

  return "Player1 HAD AN AVERAGE OF " .. (avgMatch[1]/#AllGameInfo) .. " MATCHES!", "Player2 HAD AN AVERAGE OF " .. (avgMatch[2]/#AllGameInfo) .. " MATCHES!"
  
end


---Print the number of games played
function getGamesPlayed()
  
  return "GAMES PLAYED: " .. #AllGameInfo
  
end


---Get the statistics from all of the games
-- number of games played
-- average matches per player
-- game with most guesses
-- game with least guesses
-- average guesses per game
-- longest matching streak
-- player that won the most
function getStats()

  local gamesPlayed = getGamesPlayed()
  local avgMatchP1, avgMatchP2 = getAvgMatch()
  local mostGuess, leastGuess, avgGuess = getGuessStats()
  local longestStreak = getLongestStreak()
  local mostWon = getMostWon()
  
  return {gamesPlayed, avgMatchP1, avgMatchP2, mostGuess, leastGuess, avgGuess, longestStreak, mostWon}
  
end


---clears all variables created by this file
function clear()
  
  AllGameInfo = {}
  
end