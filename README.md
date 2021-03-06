Encog Micro Edition
====================

### OVERVIEW

A port of the Encog neural network project to Java Micro Edition, 
as well as supporting server-side projects that allow a neural network 
to be trained server-side, and then have the trained model extracted and 
transferred to the mobile phone for neural net execution in real-time on the mobile device.

### PROJECT ARCHITECTURE

Details about the Encog ME architecture are available on the website:
https://github.com/barbeau/EncogME/wiki

### All projects 

1. **EncogCoreJavaME** - Core neural net code ported to Java Micro Edition
2. **EncogDesktop** - Testing desktop app used to produce neural net training and testing times in an desktop environment 
3. **EncogJavaMEApp** - Java ME app, which implements a GUI and network communication with server on top of EncogCoreJavaME
4. **EncogModelGenerator** - Destkop software used to train a neural network and store the trained model as XML in a database
5. **wsEncogResults** - Glassfish web app project, which receives requests from EncogJavaMEApp for trained neural net models, retrieved the trained models from the database, and returns them to the mobile app
