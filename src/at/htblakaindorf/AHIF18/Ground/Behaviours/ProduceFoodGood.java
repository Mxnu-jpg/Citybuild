package at.htblakaindorf.AHIF18.Ground.Behaviours;

/**
 * Good Produce-Behaviour for {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Building}
 * placed on a {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Flowers} tile
 *
 * @author Marcel Schmidl
 * @version 1.0
 * */
public class ProduceFoodGood implements ProduceBehaviour{
    @Override
    public int[] produce(int[] earnings) {
        if(!(earnings[0] <= 0))
        earnings[0] = (int) (earnings[0] * 1.4);
        return earnings;
    }
}
