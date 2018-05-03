# MissionDesigner

ToDo:
modify regular grid to take in bounding coordinates and a delta calculator, not just gps
modify greedy algorithm etc. to take in a cost function, not just the GPS costs specified
modify generation of grid to be for region [0,1] x [1,0] i.e. for a box (x,y) (x + x', y) (x, y + y') (x + x', y + y') , any point (a, b) within that box gets mapped to (a-x/x', b-y/y')
modify regularTraveralGridQuad for the case where lowest lat could be the same as lowest long, i.e. low lat, low long, hi lat, hi long might not be unique
modify mapMissionBase so that operationalAltitude is not hard-coded
