package at.htblakaindorf.AHIF18.Ground.Behaviours;

/**
 * Good Produce-Behaviour for {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Building}
 * placed on a {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Flowers} tile
 *
 * @author Marcel Schmidl
 * @version 1.0
 * */
public class  ProduceGood implements ProduceBehaviour{
    @Override
    public int[] produce(int[] earnings) {

        for (int i = 0; i < earnings.length; i++) {
            earnings[i] = (int) (earnings[i] * 1.2);
        }
        return earnings;
    }
}
