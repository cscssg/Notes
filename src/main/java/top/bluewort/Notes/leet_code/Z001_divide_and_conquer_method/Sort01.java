package top.bluewort.Notes.leet_code.Z001_divide_and_conquer_method;

/**
 * 1.把一个复杂的问题分成两个或更多的相同或相似的子问题，再把子问题分成更小的子问题……直到最后子问题可以简单的直接求解，原问题的解即子问题的解的合并。
 *
 * 2.分治策略是对于一个规模为n的问题，若该问题可以容易地解决（比如说规模n较小）则直接解决，否则将其分解为k个规模较小的子问题，这些子问题互相独立且与原问题形式相同，递归地解这些子问题，然后将各子问题的解合并得到原问题的解。
 *
 * 分治-分组排序
 * 思路：1 对数组进行分组  递归分组 直至每组只有一个元素   因为单元素数组是有序的
 * 2 对两个分组进行排序  将最小的取出放入合并数组中  直至有一个分组取完  把另一个数组剩余元素放入合并数组末尾
 * 3 递归合并 直至成功
 */
public class Sort01 {
    public static void main(String[] args) {
        int arr[] = new int[]{2,3,4,3,4,5,6,8,9,4,3,1,0,4};
        MergeSort(arr,0,arr.length-1);
        for(int x:arr){
            System.out.println("x::"+x);
        }
    }
    static void MergeIt(int[] arr,int left,int mid,int right){
        int le[] = new int[mid-left+1];
        int ri[] = new int[right-mid];

        for(int i=0;i<le.length;i++){
            le[i] = arr[i+left];
        }

        for(int i=0;i<ri.length;i++){
            ri[i] = arr[i+mid+1];
        }

        int l=0,r=0,v=le[0];
        for(int i=left;i<=right;i++){
            if(l>=le.length&&r>=ri.length){
                break;
            }else if(l>=le.length&&r<ri.length){
                v=ri[r];
                ++r;
            }else if(l<le.length&&r>=ri.length){
                v=le[l];
                ++l;
            }else {
                if(le[l]>=ri[r]){
                    v=ri[r];
                    r++;
                }else {
                    v=le[l];
                    l++;
                }
            }
            arr[i]=v;
        }
    }
    //合并排序
    static void MergeSort(int[] arr,int left,int right){
        // 单元素数组是有序的不需要排序
        if(right>left){
            int mid = (right+left)/2;
            //左排序
            MergeSort(arr,left,mid);
            //右排序
            MergeSort(arr,mid+1,right);
            //自排序（保证左右两个数组都是有序的）
            MergeIt(arr,left,mid,right);
        }
    }
}
