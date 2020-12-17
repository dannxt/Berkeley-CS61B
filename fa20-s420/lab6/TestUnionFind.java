import org.junit.Assert;

public class TestUnionFind {
    public static void testUnion() {
        UnionFind unionFind = new UnionFind(10);
        for (int i = 0; i < unionFind.getNumVertices(); i++) {
            Assert.assertEquals(-1, unionFind.parent(i));
        }
        System.out.println("Test: Vertices are separated. Passed!");
        unionFind.connect(1, 2);
        Assert.assertEquals(2, unionFind.parent(1));
        Assert.assertEquals(-2, unionFind.parent(2));
        unionFind.connect(3, 4);
        unionFind.connect(1, 4);
        Assert.assertEquals(2, unionFind.parent(1));
        Assert.assertEquals(4, unionFind.parent(2));
        Assert.assertEquals(4, unionFind.parent(3));
        Assert.assertEquals(-4, unionFind.parent(4));
        Assert.assertTrue(unionFind.isConnected(2, 3));
        unionFind.connect(1, 4);
        Assert.assertTrue(unionFind.isConnected(1, 1));
    }

    public static void main(String[] args) {
        testUnion();
    }
}
