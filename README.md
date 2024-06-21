Dejare este pequeño aporte para todos aquellos que estan aprendiendo sobre arboles binarios.
Son dos arvhivos que permitiran que puedan imprimir un arbol de forma jerarquica en consola.

Primero

En su clase Node debe considerar lo sig:
1. Debe tener los sigs campos
   private String id; // solo para el print del docente
   private boolean viewed; // solo para el print del docente
2. En el constructor agregar la generacion del id
   public Node(int value) {
        this.value = value;
        this.id = UUID.randomUUID().toString();
    }
3. Debe tener las siguientes funciones
   getLeft, getRight, getValue, getId, getViewed
   isLeaf, hasTwoSon, getChildren

Segundo

Los archivos son que debe adicionar son:
1. TBPrint
   Interfaz que se debe implementar en su clase arbol que tenga, el cual solo requiere que implemente el metodo getRoot, si ya tiene dicho metodo genial.
2. TBPrintUtil
   Arreglar los imports de dependencias

Tercero

En algun metodo main agregar algo parecido a lo siguiente

   public static void main(String[] args) {
       TreeBinary tb = new TreeBinary(); // clase de su arbol binario
       //... llamar a algun metodo que cargue datos
       TBPrintUtil.print(tb); // pasandole a la clase utilitaria de impresion el arbol que implementa la interfaz TBPrint
   }



Para aprender desde cero y de manera didactica el tema de arboles te dejo el sig. curso de árboles del canal MathLogic:
https://www.youtube.com/playlist?list=PLJeMuvKPxpu2UGbpuFrcEAPCYhZzdCpBk
