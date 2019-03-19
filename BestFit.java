//package intro;

import java.util.*;
import java.lang.Math; 
import java.io.*;
import javafx.util.Pair;

class NodeBin{
	NodeBin backpointer;
	NodeBin pointer;
	NodeBin left;
	NodeBin right;
	int id;
	int cap;
	int height;
	NodeObj next;
}

class NodeObj{
	NodeBin opointer;
	int obj_id;
	int s;
	NodeObj oleft;
	NodeObj oright;
	int oheight;
}

public class BestFit {
	ArrayList<Pair<Integer,Integer>> arr; 
	NodeBin abc;
	NodeBin xyz;
	NodeObj c;
	NodeBin a;
	NodeBin b;
	NodeBin rootCap ;
	NodeBin rootId;
	NodeObj genoroot;
	int objs;
	int i=0;
	
	NodeBin newNode(int id, int cap ) {
		NodeBin n = new NodeBin();
		n.id = id;
		n.cap = cap;
		n.left = null;
		n.right = null;
		n.height = 1;
		return n;
	}
	
	NodeObj newObj(int obj_id, int s) {
		NodeObj n = new NodeObj();
		n.obj_id = obj_id;
		n.s = s;
		n.oleft = null;
		n.oright = null;
		n.oheight = 1;
		return n;
	}
	
	static int HeightFun(NodeBin n) {
		if (n==null) {
			return 0;
		}
		else {
			return n.height;
		}
	}
	
	static int oHeightFun(NodeObj n) {
		if (n==null) {
			return 0;
		}
		else {
			return n.oheight;
		}
	}
	
	NodeBin InsertBinCap(NodeBin n, int id, int cap) {
		if (n==null) { 
			if (rootCap==null) {
				rootCap = newNode(id, cap);
				b = rootCap;
			}
			else {
				b = newNode(id, cap);
			}
			
			return b;
		}
		if(n.cap<cap) {	
			//System.out.println(2001);
			n.right = InsertBinCap(n.right, id, cap);
		}
		else if(n.cap>cap){
			//System.out.println(3001);
			n.left = InsertBinCap(n.left, id, cap);
		}
		else {
			if(n.id<id) {
				n.right = InsertBinCap(n.right, id, cap);
			}
			else {
				n.left = InsertBinCap(n.left, id, cap);
			}
		}
		if (n.left!=null && n.right!=null) {
			n.height = 1 + Math.max(n.left.height, n.right.height);
		}
		else if (n.left==null && n.right!=null) {
			n.height = 1 + n.right.height;
		}
		else if (n.right==null && n.left!=null) {
			n.height = 1 + n.left.height;
		}
		else {
			n.height=1;
		}
		
		if (HeightFun(n.right) - HeightFun(n.left) > 1) {
			if (cap > n.right.cap) {
				return AVLRotate.Case1(n);
			}
			else if (cap < n.right.cap) {
				n.right=AVLRotate.Case2(n.right);
				return AVLRotate.Case1(n);
			}
			else {
				if(id<n.right.id) {
					n.right=AVLRotate.Case2(n.right);
					return AVLRotate.Case1(n);
				}
				else {
					return AVLRotate.Case1(n);
				}
			}
		}
		if (HeightFun(n.right) - HeightFun(n.left) < -1) {
			if (cap < n.left.cap) {
				return AVLRotate.Case2(n);
			}
			else if (cap > n.left.cap){
				n.left = AVLRotate.Case1(n.left);
				return AVLRotate.Case2(n);
			}
			else {
				if(id<n.left.id) {
					return AVLRotate.Case2(n);
				}
				else {
					n.left = AVLRotate.Case1(n.left);
					return AVLRotate.Case2(n);
				}
			}
		}
		return n;
	}
	
	NodeBin InsertBinId(NodeBin n, int id, int cap) {
		if (n==null) {
			//System.out.println(1000);
			if (rootId==null) {
				rootId = newNode(id, cap);
				a = rootId;
			}
			else {
				a = newNode(id, cap);
			}
			return a;
		}
		if(n.id<id) {	
			//System.out.println(2001);
			n.right = InsertBinId(n.right, id, cap);
		}
		else {
			//System.out.println(3001);
			n.left = InsertBinId(n.left, id, cap);
		}
		if (n.left!=null && n.right!=null) {
			n.height = 1 + Math.max(n.left.height, n.right.height);
		}
		else if (n.left==null && n.right!=null) {
			n.height = 1 + n.right.height;
		}
		else if (n.right==null && n.left!=null) {
			n.height = 1 + n.left.height;
		}
		else {
			n.height=1;
		}
		
		if (HeightFun(n.right) - HeightFun(n.left) > 1) {
			if (id >= n.right.id) {
				return AVLRotate.Case1(n);
			}
			else {
				n.right=AVLRotate.Case2(n.right);
				return AVLRotate.Case1(n);
			}
		}
		if (HeightFun(n.right) - HeightFun(n.left) < -1) {
			if (id <= n.left.id) {
				return AVLRotate.Case2(n);
			}
			else {
				n.left = AVLRotate.Case1(n.left);
				return AVLRotate.Case2(n);
			}
		}
		return n;
	}
		
	public void add_bin(int id, int cap) {
		this.rootCap = InsertBinCap(rootCap, id, cap);
		this.rootId = InsertBinId(rootId, id, cap);
		b.pointer = a;
		a.backpointer = b;
	}
	
	NodeBin DeleteBin(NodeBin n, int id, int cap) {
		//System.out.println("hi");
		if (n.cap < cap) {
			n.right = DeleteBin(n.right, id, cap);
		}
		else if (n.cap > cap) {
			n.left = DeleteBin(n.left, id, cap);
		}
		else if(n.id != id){
			if(n.id<id) {
				n.right = DeleteBin(n.right, id, cap);
			}
			else {
				n.left = DeleteBin(n.left, id, cap);
			}
		}
		else {
				if (n.left==null && n.right==null) {
					n = null;
					return n;
				}
				else if(n.left ==null) {
					n = n.right;
				}
				else if(n.right == null) {
					n = n.left;
				}
				else {
					NodeBin m=n.right;
					while (m.left!=null) {
						m=m.left;
					}
					n.cap = m.cap;
					n.id = m.id;
					m.pointer.backpointer = n;
					n.pointer = m.pointer;
					n.right = DeleteBin(n.right, m.id, m.cap);
				}
		}
		if (n.left!=null && n.right!=null) {
			n.height = 1 + Math.max(n.left.height, n.right.height);
		}
		else if (n.left==null && n.right!=null) {
			n.height = 1 + n.right.height;
		}
		else if (n.right==null && n.left!=null) {
			n.height = 1 + n.left.height;
		}
		else {
			n.height=1;
		}
		if (HeightFun(n.right) - HeightFun(n.left) > 1) {
			if (HeightFun(n.right.right) > HeightFun(n.right.left)) {
				return AVLRotate.Case1(n);
			}
			else {
				n.right=AVLRotate.Case2(n.right);
				return AVLRotate.Case1(n);
			}
		}
		if (HeightFun(n.right) - HeightFun(n.left) < -1) {
			if (HeightFun(n.left.right) <= HeightFun(n.left.left)) {
				return AVLRotate.Case2(n);
			}
			else {
				n.left = AVLRotate.Case1(n.left);
				return AVLRotate.Case2(n);
			}
		}
		return n;	
	}
	
	NodeObj GenInsertObj(NodeObj n, int obj_id, int s) {
		if (n==null) {
			//System.out.println(1000);
			if (genoroot==null) {
				genoroot = newObj(obj_id, s);
				c = genoroot;
			}
			else {
				c = newObj(obj_id, s);
			}
			return c;
		}
		if(n.obj_id<obj_id) {	
			n.oright = GenInsertObj(n.oright, obj_id, s);
		}
		else {
			//System.out.println(3001);
			n.oleft = GenInsertObj(n.oleft, obj_id, s);
		}
		if (n.oleft!=null && n.oright!=null) {
			n.oheight = 1 + Math.max(n.oleft.oheight, n.oright.oheight);
		}
		else if (n.oleft==null && n.oright!=null) {
			n.oheight = 1 + n.oright.oheight;
		}
		else if (n.oright==null && n.oleft!=null) {
			n.oheight = 1 + n.oleft.oheight;
		}
		else {
			n.oheight=1;
		}
		
		if (oHeightFun(n.oright) - oHeightFun(n.oleft) > 1) {
			if (obj_id >= n.oright.obj_id) {
				return AVLRotate.oCase1(n);
			}
			else {
				n.oright=AVLRotate.oCase2(n.oright);
				return AVLRotate.oCase1(n);
			}
		}
		if (oHeightFun(n.oright) - oHeightFun(n.oleft) < -1) {
			if (obj_id <= n.oleft.obj_id) {
				return AVLRotate.oCase2(n);
			}
			else {
				n.oleft = AVLRotate.oCase1(n.oleft);
				return AVLRotate.oCase2(n);
			}
		}
		return n;
	}
	
	NodeObj InsertObj(NodeObj n, int obj_id, int s) {
		if (n==null) {
			//System.out.println(1000);
			return newObj(obj_id, s);
		}
		if(n.obj_id<obj_id) {	
			//System.out.println(2001);
			n.oright = InsertObj(n.oright, obj_id, s);
		}
		else {
			//System.out.println(3001);
			n.oleft = InsertObj(n.oleft, obj_id, s);
		}
		if (n.oleft!=null && n.oright!=null) {
			n.oheight = 1 + Math.max(n.oleft.oheight, n.oright.oheight);
		}
		else if (n.oleft==null && n.oright!=null) {
			n.oheight = 1 + n.oright.oheight;
		}
		else if (n.oright==null && n.oleft!=null) {
			n.oheight = 1 + n.oleft.oheight;
		}
		else {
			n.oheight=1;
		}
		
		if (oHeightFun(n.oright) - oHeightFun(n.oleft) > 1) {
			if (obj_id >= n.oright.obj_id) {
				return AVLRotate.oCase1(n);
			}
			else {
				n.oright=AVLRotate.oCase2(n.oright);
				return AVLRotate.oCase1(n);
			}
		}
		if (oHeightFun(n.oright) - oHeightFun(n.oleft) < -1) {
			if (obj_id <= n.oleft.obj_id) {
				return AVLRotate.oCase2(n);
			}
			else {
				n.oleft = AVLRotate.oCase1(n.oleft);
				return AVLRotate.oCase2(n);
			}
		}
		return n;
	}
	
	void AddObj(NodeBin n, int obj_id, int s) {
		NodeBin d = n;
		while (d.right!=null) {
			d=d.right;
		}
		abc= d.pointer;
		if(abc.cap >= s) {
			int x = d.cap - s;
			int y = d.id;
			rootCap = DeleteBin(rootCap, d.id, d.cap);
			rootCap = InsertBinCap(rootCap, y, x);
			b.pointer = abc;
			abc.backpointer = b;
			//c.opointer = b;
			abc.cap = abc.cap - s;
			//System.out.println(abc.id);
			if (abc.next == null) {
				NodeObj o = new NodeObj();
				o.obj_id = obj_id;
				o.s = s;
				o.oheight = 1;
				abc.next = o;
			}
			else {
				abc.next = InsertObj(abc.next, obj_id, s);
			}
			c.opointer = abc;
		}
		else {
			System.out.println("Object too big");
			throw new NullPointerException();
		}
	}
	
	public int add_object(int obj_id, int s) {
		this.genoroot = GenInsertObj(genoroot, obj_id, s);
		AddObj(rootCap, obj_id, s);		
		return abc.id;
	}
	
	void oSearchObj(NodeObj n, int obj_id) {
		if (n.obj_id < obj_id) {
			oSearchObj(n.oright, obj_id);
		}
		else if (n.obj_id > obj_id) {
			oSearchObj(n.oleft, obj_id);
		}
		else {
			objs = n.s;
			xyz = n.opointer;
		}
	}
	
	NodeObj oDelObj(NodeObj n, int obj_id) {
		if (n.obj_id < obj_id) {
			n.oright = oDelObj(n.oright, obj_id);
		}
		else if (n.obj_id > obj_id) {
			n.oleft = oDelObj(n.oleft, obj_id);
		}
		else {
			if (n.oleft==null && n.oright==null) {
				n = null;
				return n;
			}
			else if(n.oleft ==null) {
				n = n.oright;
			}
			else if(n.oright == null) {
				n = n.oleft;
			}
			else {
				NodeObj m=n.oright;
				while (m.oleft!=null) {
					m=m.oleft;
				}
				n.s = m.s;
				n.obj_id = m.obj_id;
				n.opointer = m.opointer;
				n.oright = oDelObj(n.oright, m.obj_id);
			}
		}
		if (n.oleft!=null && n.oright!=null) {
			n.oheight = 1 + Math.max(n.oleft.oheight, n.oright.oheight);
		}
		else if (n.oleft==null && n.oright!=null) {
			n.oheight = 1 + n.oright.oheight;
		}
		else if (n.oright==null && n.oleft!=null) {
			n.oheight = 1 + n.oleft.oheight;
		}
		else {
			n.oheight=1;
		}
		if (oHeightFun(n.oright) - oHeightFun(n.oleft) > 1) {
			if (oHeightFun(n.oright.oright) > oHeightFun(n.oright.oleft)) {
				return AVLRotate.oCase1(n);
			}
			else {
				n.oright=AVLRotate.oCase2(n.oright);
				return AVLRotate.oCase1(n);
			}
		}
		if (oHeightFun(n.oright) - oHeightFun(n.oleft) < -1) {
			if (oHeightFun(n.oleft.oright) <= oHeightFun(n.oleft.oleft)) {
				return AVLRotate.oCase2(n);
			}
			else {
				n.oleft = AVLRotate.oCase1(n.oleft);
				return AVLRotate.oCase2(n);
			}
		}
		
		return n;	
	}
	
	NodeObj DelObj(NodeObj n, int obj_id) {
		if (n.obj_id < obj_id) {
			n.oright = DelObj(n.oright, obj_id);
		}
		else if (n.obj_id > obj_id) {
			n.oleft = DelObj(n.oleft, obj_id);
		}
		else {
			if (n.oleft==null && n.oright==null) {
				n = null;
				return n;
			}
			else if(n.oleft ==null) {
				n = n.oright;
			}
			else if(n.oright == null) {
				n = n.oleft;
			}
			else {
				NodeObj m=n.oright;
				while (m.oleft!=null) {
					m=m.oleft;
				}
				n.s = m.s;
				n.obj_id = m.obj_id;
				n.oright = DelObj(n.oright, m.obj_id);
			}
		}
		if (n.oleft!=null && n.oright!=null) {
			n.oheight = 1 + Math.max(n.oleft.oheight, n.oright.oheight);
		}
		else if (n.oleft==null && n.oright!=null) {
			n.oheight = 1 + n.oright.oheight;
		}
		else if (n.oright==null && n.oleft!=null) {
			n.oheight = 1 + n.oleft.oheight;
		}
		else {
			n.oheight=1;
		}
		if (oHeightFun(n.oright) - oHeightFun(n.oleft) > 1) {
			if (oHeightFun(n.oright.oright) > oHeightFun(n.oright.oleft)) {
				return AVLRotate.oCase1(n);
			}
			else {
				n.oright=AVLRotate.oCase2(n.oright);
				return AVLRotate.oCase1(n);
			}
		}
		if (oHeightFun(n.oright) - oHeightFun(n.oleft) < -1) {
			if (oHeightFun(n.oleft.oright) <= oHeightFun(n.oleft.oleft)) {
				return AVLRotate.oCase2(n);
			}
			else {
				n.oleft = AVLRotate.oCase1(n.oleft);
				return AVLRotate.oCase2(n);
			}
		}
		return n;
	}
	
	public int delete_object(int obj_id) {
		oSearchObj(genoroot, obj_id);
		this.genoroot = oDelObj(genoroot, obj_id);	
		NodeBin y = xyz.backpointer;
		xyz.next = DelObj(xyz.next, obj_id);
		xyz.cap = xyz.cap + objs;
		rootCap = DeleteBin(rootCap, y.id, y.cap);		
		rootCap = InsertBinCap(rootCap, xyz.id, xyz.cap);
		//System.out.println(b.id);
		b.pointer = xyz;
		xyz.backpointer = b;
		return b.id;
	}
	
	void SearchId(NodeBin n, int id) {
		if(n.id<id) {	
			SearchId(n.right, id);
		}
		else if (n.id>id) {
			SearchId(n.left, id);
		}
		else {
			InorderPrint(n.next);
		}
	}
	
	void InorderPrint(NodeObj n){
		if (n==null) {
			return;
		}
		InorderPrint(n.oleft);
		//System.out.println(n.obj_id);
		//System.out.println(n.s);
		arr.add(new Pair<Integer,Integer> (n.obj_id,n.s));
		InorderPrint(n.oright);
	}
	
	public List<Pair<Integer,Integer>> contents(int id) {
		arr = new ArrayList<Pair<Integer,Integer>>();
		SearchId(rootId, id); 
		return arr;
	}
	
	// List<Pair<Integer,Integer>>
//	void InorderPrint(NodeBin n){
//		if (n==null) {
//			return;
//		}
//		InorderPrint(n.left);
//		//System.out.println(n.id);
//		System.out.println(n.cap);
//		InorderPrint(n.right);
//	}
	
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		BestFit asd = new BestFit();
//		try (BufferedReader b = new BufferedReader(new FileReader(args[0]))){
//			String a = b.readLine();
//			while (a!=null) {
//				String[] boss = a.split(" ");
//				if (Integer.parseInt(boss[0]) == 1) {
//					asd.add_bin(Integer.parseInt(boss[1]), Integer.parseInt(boss[2]));
//				}
//				else if (Integer.parseInt(boss[0]) == 2) {
//					System.out.println(asd.add_object(Integer.parseInt(boss[1]), Integer.parseInt(boss[2])));
//				}
//				else if (Integer.parseInt(boss[0]) == 3) {
//					System.out.println(asd.delete_object(Integer.parseInt(boss[1])));
//				}
//				else if (Integer.parseInt(boss[0]) == 4) {
//					List<Pair<Integer,Integer>> uh = asd.contents(Integer.parseInt(boss[1]));
//					int i=0;
//					while (i < uh.size()){
//						//int po = uh.getKey();
//						System.out.println(uh.get(i).getKey()+" "+uh.get(i).getValue());
//						i++;
//						//uh=uh.next();
//					}
//				}
//				a = b.readLine();
//			}
//			}
//		catch (IOException e) {
//			return;
//		}
//		catch (NullPointerException f){
//			return;
//		}
//		
//		
//		
////		asd.add_bin(110, 4);
////		asd.add_bin(100, 2);
////		asd.add_bin(120, 10);
////		asd.add_bin(150, 13);
////		asd.add_bin(140, 15);
////		asd.add_object(222, 1);
////		//System.out.println(asd.b.id);
////		asd.add_object(323,2);
////		asd.contents(140);
////    	//System.out.println(asd.rootCap.right.right.pointer.id);
//////		System.out.println(asd.rootCap.right.pointer.next.oright.obj_id);
////		//System.ut.println(asd.rootCap.right.right.pointer.next.obj_id);
////		//asd.add_object(34, 2);
//////		asd.add_object(32, 10);
//////		asd.add_object(11, 1);
//////		System.out.println(asd.genoroot.s);
//////		System.out.println(asd.genoroot.oright.obj_id);
////		asd.delete_object(323);
////		asd.contents(140);
//////		System.out.println(asd.rootCap.right.pointer.next.obj_id);
////		//System.out.println(asd.genoroot.oright.obj_id);
////		
////		
//////		asd.add_object(112, 6);
//////		System.out.println(asd.rootCap.right.right.pointer.next.obj_id);
////		//asd.rootCap = asd.DeleteBin(asd.rootCap, 15 , 13);
////		//System.out.println(1234);
////		//System.out.println(asd.rootCap.right.left.cap);
////		//asd.rootId = asd.add_bin(11, 2);
////		//asd.rootCap = asd.add_bin(4,
//	}

}
