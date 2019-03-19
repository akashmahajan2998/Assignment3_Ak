//package intro;

public class AVLRotate {
	public static NodeBin Case1(NodeBin n) {
		NodeBin tempnode = n.right;
		NodeBin temp = tempnode.left;
		tempnode.left = n;
		n.right = temp;
		n.height = Math.max(BestFit.HeightFun(n.left), BestFit.HeightFun(temp))+1;
		tempnode.height = Math.max(BestFit.HeightFun(n), BestFit.HeightFun(tempnode.right))+1;
		return tempnode;
	}
	
	public static NodeObj oCase1(NodeObj n) {
		NodeObj tempnode = n.oright;
		NodeObj temp = tempnode.oleft;
		tempnode.oleft = n;
		n.oright = temp;
		n.oheight = Math.max(BestFit.oHeightFun(n.oleft), BestFit.oHeightFun(temp))+1;
		tempnode.oheight = Math.max(BestFit.oHeightFun(n), BestFit.oHeightFun(tempnode.oright))+1;
		return tempnode;
	}
	
	public static NodeBin Case2(NodeBin n) {
		NodeBin tempnode = n.left;
		NodeBin temp = tempnode.right;
		tempnode.right = n;
		n.left = temp;
		n.height = Math.max(BestFit.HeightFun(n.right), BestFit.HeightFun(temp))+1;
		tempnode.height = Math.max(BestFit.HeightFun(n), BestFit.HeightFun(tempnode.left))+1;
		return tempnode;
	}
	
	public static NodeObj oCase2(NodeObj n) {
		NodeObj tempnode = n.oleft;
		NodeObj temp = tempnode.oright;
		tempnode.oright = n;
		n.oleft = temp;
		n.oheight = Math.max(BestFit.oHeightFun(n.oright), BestFit.oHeightFun(temp))+1;
		tempnode.oheight = Math.max(BestFit.oHeightFun(n), BestFit.oHeightFun(tempnode.oleft))+1;
		return tempnode;
	}
}
