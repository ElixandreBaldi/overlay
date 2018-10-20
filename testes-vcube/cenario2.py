import os

prefix = "java -cp lib/peersim-1.0.5.jar:lib/djep-1.0.0.jar:lib/jep-2.3.0.jar:vcube.jar overlay.Overlay -Xmx8g"

for i in range(3, 16):
    tam = 2 ** i;
    for j in range(0, 10):    
        pathArg = "cenario2/"+str(tam)+".cfg"
        os.system(prefix+" "+pathArg)
