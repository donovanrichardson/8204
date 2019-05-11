import csv
class LitFrq:
    
    def __init__(self, let, frq):
        self.let = let
        self.lis = [let]
        self.frq = frq

    def __repr__(self):
        return repr ((self.let, self.lis, self.frq))
    
    def mrg(self, litfrq):
        self.lis.extend(litfrq.lis)
        self.frq = self.frq + litfrq.frq

kissuv = []
freaks = []

with open('/Users/donovanrichardson/Dropbox/8055/haquathon/frq.csv', newline = '') as freq:
    reader = csv.reader(freq, delimiter = ',')
    for row in reader:
        kissuv.append(row)
kissuv = kissuv[1:]
for r in kissuv:
    r[1] = int(r[1])
print(kissuv)
for r in kissuv:
    freaks.append(LitFrq(r[0],r[1]))

s = sorted(freaks, key =lambda lf: lf.frq)

def printsort(lyst):
    for item in lyst:
        print(lyst.index(item), item)

printsort(s)

def makesort(lyst):
    lyst = sorted(lyst, key =lambda lf: lf.frq)
    for item in lyst:
        print(lyst.index(item), item)
    return lyst

def mergepops(hi, lo):
    global s
    s[hi].mrg(s[lo])
    s.pop(lo)
    s = makesort(s)



