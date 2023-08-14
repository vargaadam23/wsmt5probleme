namespace Wsmt5probleme
{
    class Nod
    {
        public Nod stanga;
        public Nod dreapta;
        public int index;
        public String valoare;

        public Nod(String valoare, int index)
        {
            this.valoare = valoare;
            this.stanga = null;
            this.dreapta = null;
            this.index = index;
        }
    }

    class Aux
    {
        public Aux(int index, int parinte, int pozitie, String valoare)
        {
            this.index = index;
            this.parinte = parinte;
            this.pozitie = pozitie;
            this.valoare = valoare;
        }
        public int index;
        public String valoare;
        public int parinte;
        public int pozitie;
    }

    class FileParser
    {
        static String FILE_PATH = "/home/school/Projects/wsmt5probleme/";

        private List<Aux> ReadLines()
        {
            List<Aux> set = new List<Aux>();
            Boolean radacina = false;
            Console.Write("Dati numele fisierului: ");
            string fileName = Console.ReadLine();
            foreach (string line in System.IO.File.ReadLines(@FileParser.FILE_PATH + fileName))
            {
                string[] dateList = line.Split(',');
                Aux aux = new Aux(Int32.Parse(dateList[0]), Int32.Parse(dateList[1]), Int32.Parse(dateList[2]), dateList[3]);
                if (dateList.Length != 4)
                {
                    throw new Exception("Date insuficiente");
                }
                if (aux.parinte < 0)
                {
                    if (!radacina)
                    {
                        radacina = true;
                    }
                    else
                    {
                        throw new Exception("Nodul nu poate avea 2 radacini");
                    }
                }
                set.Add(aux);
            }

            if (!radacina)
            {
                throw new Exception("Arborele trebuie sa aiba o radacina");
            }
            return set;
        }

        private Dictionary<int, Nod> listToHashmap(List<Aux> list)
        {
            
            Dictionary<int,Nod> map = new Dictionary<int, Nod>();
            foreach (Aux item in list)
            {
                Nod nod = new Nod(item.valoare, item.index);
                map[item.index] = nod;
            }
            return map;
        }

        public ArboreBinar genereazaArbore()
        {
            List<Aux> list = this.ReadLines();
            ArboreBinar arbore = new ArboreBinar();
            Dictionary<int, Nod> map = this.listToHashmap(list);
            foreach (Aux item in list)
            {
                if (item.parinte < 0)
                {
                    arbore.radacina = map[item.index];
                }
                else
                {
                    Nod nod = map[item.index];
                    Nod parinte = map[item.parinte];
                    if (item.pozitie == 0)
                    {
                        parinte.stanga = nod;
                    }
                    else
                    {
                        if (item.pozitie == 1)
                        {
                            parinte.dreapta = nod;
                        }
                        else
                        {
                            throw new Exception("Parintele contine nodul");
                        }
                    }
                }
            }
            return arbore;
        }


    }
    class ArboreBinar
    {
        public Nod radacina;

        public void parcurgere(){
            Queue<Nod> queue = new Queue<Nod>();
            queue.Enqueue(this.radacina);
            while(queue.Count!=0){
                Nod current = queue.Dequeue();
                Console.Write(current.index+" ");
                if(current.stanga!=null){
                    queue.Enqueue(current.stanga);
                }
                if(current.dreapta!=null){
                    queue.Enqueue(current.dreapta);
                }
            }
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            FileParser fp = new FileParser();
            ArboreBinar arbore = fp.genereazaArbore();
            arbore.parcurgere();
        }
    }
}