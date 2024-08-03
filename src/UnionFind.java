public class UnionFind {

    // number of elements in this union find
    private int size;

    // used to track the size of each of the component
    private int[] sz;

    // id[i] points to the parent of i, if id[i] = i then i is a root node
    private int[] id;

//     a  b  c  d  e  f  g
//    [0, 1, 2 ,3 ,4, 5, 4] - id
//     0  1  2  3  4  5  6
//
//    if id[2] = 2, 2 is root (self root)
//    id[6] = 4; root of 6 is 4    e <--- g

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

    // if the root of p and q same, they are connected (same component)
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // return the size of the component p belongs to
    public int componentSize(int p) {
        return sz[find(p)];
    }

    public int size() {
        return size;
    }

    public int components() {
        return numComponents;
    }

    public void unify(int p, int q) {
        int root1 = find(p);
        int root2 = find(q);

        if(root1 == root2) // elements p and q is already in the same group
            return;

        // we are considering which component is bigger
        if(sz[root1] < sz[root2]) { // if component with root2 is bigger
            sz[root2] += sz[root1]; // size of root2 += size of root1
            id[root1] = root2; // root of root1 is now root2 (before this, it was self root)
        }
        else {
            sz[root1] += sz[root2];
            id[root2] = root1;
        }

        numComponents--; //decrease num of components
    }
}
