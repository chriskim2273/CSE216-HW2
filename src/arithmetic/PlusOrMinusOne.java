package arithmetic;

public class PlusOrMinusOne{

    private static PlusOrMinusOne[] values = {new PlusOrMinusOne(-1),new PlusOrMinusOne(1)};

    private int value;

    public PlusOrMinusOne(int value){
        this.value = value;
    }

    public static PlusOrMinusOne[] values(){
        return values;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
