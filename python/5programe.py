
class Nod:
    def __init__(self,valoare,index):
        self.valoare = valoare
        self.index = index
        self.dreapta = None
        self.stanga = None

class Aux:
    def __init__(self,valoare,index,parinte,pozitie):
        self.valoare = valoare
        self.index = index
        self.parinte = parinte
        self.pozitie = pozitie

class ArboreBinar:
    def __init__(self):
        self.radacina = None
    
    def parcurgere(self):
        queue = []
        queue.append(self.radacina)
        while len(queue)>0:
            currentNod = queue.pop(0)
            print(str(currentNod.index) + " ")
            if currentNod.stanga is not None:
                queue.append(currentNod.stanga)
            if currentNod.dreapta is not None:
                queue.append(currentNod.dreapta)
        

class FileParser:
    FILE_PATH = "/home/school/Projects/5programe/"
    def readLines(self):
        radacina = False
        set = []
        fileName = raw_input('Dati numele fisierului: ')
        numeFisier = self.FILE_PATH+fileName
        filer = open(numeFisier, 'r')
        Lines = filer.readlines()
        for line in Lines:
            print(line)
            dateList = line.split(',')
            print(dateList)
            if len(dateList) != 4:
                raise Exception("Datele sunt insuficiente")
            aux = Aux(dateList[3],int(dateList[0]),int(dateList[1]),int(dateList[2]))
            if aux.parinte < 0:
                if radacina is False:
                    print("are")
                    radacina = True
                else:
                    raise Exception("Arborele nu poate avea 2 radacini")
            set.append(aux)
        filer.close()
        if radacina == False:
            raise Exception("Arborele trebuie sa aiba radacina")
        return set

    
    def listToNodeHashap(self,list):
        map = {}
        for x in list:
            nod = Nod(x.valoare, x.index)
            map[x.index] = nod
        return map
    
    def genereazaArbore(self):
        list = self.readLines()
        arbore = ArboreBinar()
        map = self.listToNodeHashap(list)
        for item in list:
            if item.parinte < 0:
                arbore.radacina = map[item.index]
            else:
                nod = map[item.index]
                parinte = map[item.parinte]
                if item.pozitie == 0:
                    parinte.stanga = nod
                else:
                    if item.pozitie == 1:
                        parinte.dreapta = nod
                    else:
                        raise Exception("Parintele contine nodul")
        return arbore           
        
            

fileparser = FileParser()
arbore = fileparser.genereazaArbore()
arbore.parcurgere()