import os, subprocess,sys

game_dir = 'd:/simple-gohr/GOHR-ERROR/access-3395249-mm/reproducibility/captive/game'
classpath = f"{game_dir}/lib/captive.jar;{game_dir}/../jaxrs-ri/ext/*"


#game='game-data/rules/rules-01.txt'
game=sys.argv[1]

# nPieces = '5'

#-- this is a string, not a number!
nPieces = sys.argv[2]

sys.stdout.write("Rule file=" + game +", #pieces=" + nPieces+"\n")

proc=subprocess.Popen(
    ['java', 
     '-Doutput=STANDARD', 
     '-cp', classpath, 
     'edu.wisc.game.engine.Captive', 
     game, 
     nPieces
    ], 
    stdin=subprocess.PIPE, 
    stdout=subprocess.PIPE
)