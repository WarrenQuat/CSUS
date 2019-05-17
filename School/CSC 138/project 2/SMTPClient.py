#Warren Quattrocchi
#CSC 138
#Socket programming assignment 2 - Mail Client

from socket import *

#message to be sent, must end in \r\n.\r\n
msg = "\r\n I love computer networks!"
endmsg = "\r\n.\r\n"
mailserver = 'smtp.csus.edu'

#crete the socket and connect to mailserver over port 25
clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((mailserver,25))
recv = clientSocket.recv(1024).decode()
print recv
if recv[:3] != '220':
   print('220 reply not recieved from server.')

#send HELO to mailserver
print 'sending HELO'
heloCommand = 'HELO Alice\r\n'
clientSocket.send(heloCommand.encode())
recv1 = clientSocket.recv(1024).decode()
print recv1
if recv1[:3] != '250':
   print('250 reply not recieved from server.')

#mailFrom address
print 'sending mail from'
mailFromCommand = "MAIL FROM: <warren.quattrocchi@gmail.com>\r\n"
clientSocket.send(mailFromCommand.encode())
recv2 = clientSocket.recv(1024).decode()
print recv2
if recv2[:3] != '250':
   print('250 reply not recieved from server.')

#rcptTo email
print 'sending rcpt to'
rcptToCommand = "RCPT TO: <warren.quattrocchi@gmail.com>\r\n"
clientSocket.send(rcptToCommand.encode())
recv3 = clientSocket.recv(1024).decode()
print recv3
if recv3[:3] != '250':
   print('250 reply not recieved from server.')


#send data command
print 'alerting that data will be sent'
dataCommand = "DATA\r\n";
clientSocket.send(dataCommand.encode())
recv4 = clientSocket.recv(1024).decode()
print recv4 
if recv4[:3] != '354':
   print('354 reply not recieved from server.')

#send data
print 'sending data'
clientSocket.send(msg.encode())
clientSocket.send(endmsg.encode())
recv5 = clientSocket.recv(1024).decode()
print recv5
if recv1[:3] != '250':
   print('250 reply not recieved from server.')

#close socket
print 'quitting'
quitCommand = "QUIT\r\n"
clientSocket.send(quitCommand.encode())
recv6 = clientSocket.recv(1024)
print recv6
if recv6[:3] != '221':
   print('221 reply not recieved from server')

clientSocket.close()

