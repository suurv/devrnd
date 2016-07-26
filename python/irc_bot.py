# simple IRC bot using purely only socket module

from time import sleep
import time
import sys
import socket

# some params
server ="localhost"
channel="#test"
botnick="bot1"
admins=['user1','user2']



def send(msg):
	print "[+] sending: " + msg
	irc.send(msg + "\r\n")

def sendchat(msg):
	send("PRIVMSG " + channel + " :" + msg)		

def process(c):
	if c == "!die":
		send("PART %s : Terminated..." % (channel))
		irc.close()
		sys.exit(0)
	elif c == "!time":
		sendchat(str(time.time()))
	elif c == "!help":
		sendchat("not available yet...")
ready = False


irc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print "[+] Connecting:" + server

irc.connect((server,6667))

send("USER " + botnick + " " + botnick + " " + botnick + " :" + botnick)
send("NICK " + botnick) 

while 1:
	message=irc.recv(2040)
	lines = message.split("\n")
	for line in lines:
		line = line.strip()
		if line.find("PING :") != -1:
			print "[+] Detected ping"
			ping_id = line.split(":")[1]
			print "[*] " + ping_id
			send("PONG :" + ping_id)
		if line.find("End of /MOTD command.") != -1:
			send("JOIN " + channel)
			ready = True
		if ready and line.find("PRIVMSG " + channel) != -1:
			line_parts = line.split(":",3)
			user = line_parts[1].split("!")[0]
			command  = line.split("PRIVMSG " + channel +" :")[1]
			if user in admins:
				process(command)
