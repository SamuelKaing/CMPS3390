package skaing.finalproject;

public interface GameObject {
    void update();

    float getSpeed(float speed);
    float setSpeed(float speed);

    float getHealth();
    float takeDamage(float damageTaken);
    float addHealth(float amountHealed);
    float addXP(float xpGained);
}
