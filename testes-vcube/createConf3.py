import os

stepControlVCube = '20'
stepControlUpNode = '10'
nPuts = '1'
nLookups = '1'
nodosOk = ['-1','1','1','1','1','1','1','0','0']
i = 3 #cenario

for j in range(3, 16):
    tam = 2 ** j;
    path = "cenario"+str(i)+"/"+str(tam)+".cfg"
    text = '# PEERSIM VCUBE\n'
    text += 'CYCLES 10^9\n'
    text += 'SIZE 2^'+str(j)+' #100\n'
    text += 'M 10\n'
    text += 'SUCC_SIZE 4\n\n'

    text += 'random.seed 12345678\n'
    text += 'simulation.endtime CYCLES\n'
    text += 'simulation.logtime 10^6\n\n'

    text += 'simulation.experiments 1\n\n'

    text += 'network.size SIZE\n\n'

    text += 'protocol.tr UniformRandomTransport\n'
    text += '{\n'
    text += '   mindelay 0\n'
    text += '   maxdelay 0\n'
    text += '}\n\n'

    text += 'protocol.vcube overlay.vcube.VCubeProtocol \n'
    text += '{\n'
    text += '   transport tr\n'
    text += '}\n\n'

    text += 'control.controlVCube overlay.controls.ControlVCube\n'
    text += '{\n'
    text += '   protocol vcube\n'
    text += '   step '+stepControlVCube+'\n'
    text += '}\n\n'

    text += 'control.controlPut overlay.controls.ControlExecuteLookup\n'
    text += '{\n'
    text += '    protocol vcube\n'
    text += '    step 1\n'
    text += '}\n\n'

    text += 'init.create overlay.vcube.VCubeCreate\n'
    text += '{\n'
    text += '   protocol vcube\n'
    text += '   idLength 256\n'
    text += '   scenario '+str(i)+'\n'
    text += '   nPuts '+str(nPuts)+'\n'
    text += '   nLookups '+str(nLookups)+'\n'
    text += '   stepVCube '+str(stepControlVCube)+'\n'
    text += '   nodosOk '+nodosOk[i]+' # 1 para todos os nodos ativos, 0 para metade dos nodos falhos, e -1 para apenas um nodo ativo\n'
    text += '   pathOut result'+str(i)+'/'+str(tam)+'.csv\n'
    text += '}\n\n'

    with open(path, 'w') as arquivo:
        arquivo.write(text)
