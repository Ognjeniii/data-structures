public class UnionFind {

    // number of elements in this union find
    private int size;

    // used to track the size of each of the component
    private int[] sz;

    // id[i] points to the parent of i, if id[i] = i then i is a root node
    private int[] id;

    // track the number of components in union find
    private int numComponents;

    public UnionFind(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Prosleđeni parametar je manji od 0.");

        this.size = numComponents = size;
        sz = new int[size];
        id = new int[size];

        for (int i = 0; i < size; i++) {
            id[i] = i; // self root
            sz[i] = 1; // each component is originally of size 1
        }
    }

    // finds which component element p belongs to, amortized constant time
    public int find(int p) {

        // find the root of component
        int root = p;
        while (root != id[root])
            root = id[root];

        // compress the path leading back to the root.
        // doing this operation is called "path compression"
        // and is what gives us amortized time complexity
        while (p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }

        return root;
    }

    // This is an alternative recursive formulation for the find method
    // public int find(int p) {
    //   if (p == id[p]) return p;
    //   return id[p] = find(id[p]);
    // }
}
