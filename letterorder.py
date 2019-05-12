import csv
import json
kissuv = []

with open('/Users/donovanrichardson/Dropbox/8055/haquathon/letterorder.csv', newline = '') as freq:
    reader = csv.reader(freq, delimiter = ',')
    for row in reader:
        kissuv.append(row)
##print(kissuv)

kissuv[0][0] = 'z'

print(kissuv)

print(json.dumps(kissuv))
