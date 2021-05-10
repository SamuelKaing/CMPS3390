package skaing.finalproject;

public interface GameObject {
    void update();

    float getSpeed();
    void setSpeed(float speed);

    float getHealth();
    void takeDamage(float damageTaken);
    void addHealth(float amountHealed);
    void addXP(float xpGained);
}
