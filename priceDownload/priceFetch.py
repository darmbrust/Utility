#pip install yfinance
import yfinance as yf
from datetime import datetime

#"LFUND", "GFUND",
stocks = ["SPCE", "NOK", "WKHS", "CRWD", "RIVN", "TSLA", "NFLX", "VXRT", "FTEC", "FNCMX", "MCHFX", "FSCSX", "FBIFX", "FIEUX", "FEMKX", "FAGIX", "FSCSX", "PRMTX", "FBIOX", "FSRPX", "FLCEX", "VOE", "TRRDX", "VFIAX", "VWUAX", "XSW", "VOO", "PCBIX", "SPY"]
priceFile = open("prices.csv", "w")
date = datetime.now().strftime("%m/%d/%y")

for stock in stocks:
  stockPull = yf.Ticker(stock)
  if not stockPull.info:
    continue
  ticker = stockPull.info
  market_price = ""
  if ticker.get('currentPrice') != None:
    market_price = ticker['currentPrice']
  elif ticker.get('navPrice') != None:
    market_price = ticker['navPrice']
  elif ticker.get('regularMarketPreviousClose') != None:
    market_price = ticker['regularMarketPreviousClose']
  else:
    print("Failed for:")
    print(ticker)
    continue

  print('Ticker: ' + stock + " Price: " + str(market_price))
  priceFile.write(stock)
  priceFile.write(",")
  priceFile.write(str(market_price))
  priceFile.write(",")
  priceFile.write(date)
  priceFile.write("\r\n")

priceFile.close()


