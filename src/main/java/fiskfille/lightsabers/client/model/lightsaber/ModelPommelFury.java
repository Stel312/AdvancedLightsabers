package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelPommelFury extends ModelBase
{
    public ModelRenderer topRing1;
    public ModelRenderer secondaryTopRing1;
    public ModelRenderer angledRing1;
    public ModelRenderer middleRing1;
    public ModelRenderer lowerRing1;
    public ModelRenderer bottom1;
    public ModelRenderer cap1;
    public ModelRenderer side1_1;
    public ModelRenderer side2_1;
    public ModelRenderer side3_1;
    public ModelRenderer side4_1;
    public ModelRenderer topRing2;
    public ModelRenderer topRing3;
    public ModelRenderer topRing4;
    public ModelRenderer topRing5;
    public ModelRenderer topRing6;
    public ModelRenderer topRing7;
    public ModelRenderer topRing8;
    public ModelRenderer secondaryTopRing2;
    public ModelRenderer secondaryTopRing3;
    public ModelRenderer secondaryTopRing4;
    public ModelRenderer secondaryTopRing5;
    public ModelRenderer secondaryTopRing6;
    public ModelRenderer secondaryTopRing7;
    public ModelRenderer secondaryTopRing8;
    public ModelRenderer angledRing2;
    public ModelRenderer angledRing3;
    public ModelRenderer angledRing4;
    public ModelRenderer angledRing5;
    public ModelRenderer angledRing6;
    public ModelRenderer angledRing7;
    public ModelRenderer angledRing8;
    public ModelRenderer angledRing9;
    public ModelRenderer middleRing2;
    public ModelRenderer middleRing3;
    public ModelRenderer middleRing4;
    public ModelRenderer middleRing5;
    public ModelRenderer middleRing6;
    public ModelRenderer middleRing7;
    public ModelRenderer middleRing8;
    public ModelRenderer lowerRing2;
    public ModelRenderer lowerRing3;
    public ModelRenderer lowerRing4;
    public ModelRenderer lowerRing5;
    public ModelRenderer lowerRing6;
    public ModelRenderer lowerRing7;
    public ModelRenderer lowerRing8;
    public ModelRenderer bottom2;
    public ModelRenderer bottom3;
    public ModelRenderer bottom4;
    public ModelRenderer bottom5;
    public ModelRenderer bottom6;
    public ModelRenderer bottom7;
    public ModelRenderer bottom8;
    public ModelRenderer cap2;
    public ModelRenderer cap3;
    public ModelRenderer cap4;
    public ModelRenderer cap5;
    public ModelRenderer cap6;
    public ModelRenderer cap7;
    public ModelRenderer cap8;
    public ModelRenderer side1_2;
    public ModelRenderer side1_3;
    public ModelRenderer side1_4;
    public ModelRenderer side1_5;
    public ModelRenderer side1_6;
    public ModelRenderer side1_7;
    public ModelRenderer side1_8;
    public ModelRenderer side2_2;
    public ModelRenderer side2_3;
    public ModelRenderer side2_4;
    public ModelRenderer side2_5;
    public ModelRenderer side2_6;
    public ModelRenderer side2_7;
    public ModelRenderer side2_8;
    public ModelRenderer side3_2;
    public ModelRenderer side3_3;
    public ModelRenderer side3_4;
    public ModelRenderer side3_5;
    public ModelRenderer side3_6;
    public ModelRenderer side3_7;
    public ModelRenderer side3_8;
    public ModelRenderer side4_2;
    public ModelRenderer side4_3;
    public ModelRenderer side4_4;
    public ModelRenderer side4_5;
    public ModelRenderer side4_6;
    public ModelRenderer side4_7;
    public ModelRenderer side4_8;

    public ModelPommelFury()
    {
        textureWidth = 64;
        textureHeight = 32;
        topRing6 = new ModelRenderer(this, 0, 0);
        topRing6.setRotationPoint(0.0F, 0.0F, 0.0F);
        topRing6.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(topRing6, 0.0F, -2.356194490192345F, 0.0F);
        side1_7 = new ModelRenderer(this, 8, 6);
        side1_7.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1_7.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side1_7, 0.0F, -1.5707963267948966F, 0.0F);
        cap1 = new ModelRenderer(this, 8, 0);
        cap1.setRotationPoint(0.0F, 7.5F, 0.0F);
        cap1.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        side4_6 = new ModelRenderer(this, 8, 12);
        side4_6.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4_6.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side4_6, 0.0F, -2.356194490192345F, 0.0F);
        side4_8 = new ModelRenderer(this, 8, 12);
        side4_8.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4_8.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side4_8, 0.0F, -0.7853981633974483F, 0.0F);
        middleRing6 = new ModelRenderer(this, 0, 6);
        middleRing6.setRotationPoint(0.0F, 0.0F, 0.0F);
        middleRing6.addBox(-1.5F, 0.0F, 2.62F, 3, 4, 1, 0.0F);
        setRotateAngle(middleRing6, 0.0F, -2.356194490192345F, 0.0F);
        side4_4 = new ModelRenderer(this, 8, 12);
        side4_4.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4_4.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side4_4, 0.0F, 2.356194490192345F, 0.0F);
        side2_4 = new ModelRenderer(this, 8, 9);
        side2_4.setRotationPoint(0.0F, 0.0F, 0.0F);
        side2_4.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side2_4, 0.0F, 2.356194490192345F, 0.0F);
        topRing1 = new ModelRenderer(this, 0, 0);
        topRing1.setRotationPoint(0.0F, 0.0F, 0.0F);
        topRing1.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        secondaryTopRing6 = new ModelRenderer(this, 0, 2);
        secondaryTopRing6.setRotationPoint(0.0F, 0.0F, 0.0F);
        secondaryTopRing6.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(secondaryTopRing6, 0.0F, -2.356194490192345F, 0.0F);
        bottom5 = new ModelRenderer(this, 0, 13);
        bottom5.setRotationPoint(0.0F, 0.0F, 0.0F);
        bottom5.addBox(-1.5F, 0.0F, 1.62F, 3, 2, 2, 0.0F);
        setRotateAngle(bottom5, 0.0F, 3.141592653589793F, 0.0F);
        side1_3 = new ModelRenderer(this, 8, 6);
        side1_3.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1_3.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side1_3, 0.0F, 1.5707963267948966F, 0.0F);
        side2_2 = new ModelRenderer(this, 8, 9);
        side2_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        side2_2.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side2_2, 0.0F, 0.7853981633974483F, 0.0F);
        side2_6 = new ModelRenderer(this, 8, 9);
        side2_6.setRotationPoint(0.0F, 0.0F, 0.0F);
        side2_6.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side2_6, 0.0F, -2.356194490192345F, 0.0F);
        middleRing8 = new ModelRenderer(this, 0, 6);
        middleRing8.setRotationPoint(0.0F, 0.0F, 0.0F);
        middleRing8.addBox(-1.5F, 0.0F, 2.62F, 3, 4, 1, 0.0F);
        setRotateAngle(middleRing8, 0.0F, -0.7853981633974483F, 0.0F);
        topRing4 = new ModelRenderer(this, 0, 0);
        topRing4.setRotationPoint(0.0F, 0.0F, 0.0F);
        topRing4.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(topRing4, 0.0F, 2.356194490192345F, 0.0F);
        cap8 = new ModelRenderer(this, 8, 0);
        cap8.setRotationPoint(0.0F, 0.0F, 0.0F);
        cap8.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(cap8, 0.0F, -0.7853981633974483F, 0.0F);
        side2_1 = new ModelRenderer(this, 8, 9);
        side2_1.setRotationPoint(3.8F, 1.5F, 0.0F);
        side2_1.addBox(-1.5F, -2.0F, 2.6F, 3, 2, 1, 0.0F);
        setRotateAngle(side2_1, 1.5707963267948966F, -1.5707963267948966F, 0.0F);
        secondaryTopRing4 = new ModelRenderer(this, 0, 2);
        secondaryTopRing4.setRotationPoint(0.0F, 0.0F, 0.0F);
        secondaryTopRing4.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(secondaryTopRing4, 0.0F, 2.356194490192345F, 0.0F);
        topRing2 = new ModelRenderer(this, 0, 0);
        topRing2.setRotationPoint(0.0F, 0.0F, 0.0F);
        topRing2.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(topRing2, 0.0F, 0.7853981633974483F, 0.0F);
        cap5 = new ModelRenderer(this, 8, 0);
        cap5.setRotationPoint(0.0F, 0.0F, 0.0F);
        cap5.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(cap5, 0.0F, 3.141592653589793F, 0.0F);
        side1_2 = new ModelRenderer(this, 8, 6);
        side1_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1_2.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side1_2, 0.0F, 0.7853981633974483F, 0.0F);
        side4_1 = new ModelRenderer(this, 8, 12);
        side4_1.setRotationPoint(4.4F, 1.5F, 0.0F);
        side4_1.addBox(-1.5F, -2.0F, 2.6F, 3, 2, 1, 0.0F);
        setRotateAngle(side4_1, 1.5707963267948966F, -1.5707963267948966F, 0.0F);
        lowerRing4 = new ModelRenderer(this, 0, 11);
        lowerRing4.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerRing4.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(lowerRing4, 0.0F, 2.356194490192345F, 0.0F);
        side1_1 = new ModelRenderer(this, 8, 6);
        side1_1.setRotationPoint(3.3F, 1.5F, 0.0F);
        side1_1.addBox(-1.5F, -2.0F, 2.6F, 3, 2, 1, 0.0F);
        setRotateAngle(side1_1, 1.5707963267948966F, -1.5707963267948966F, 0.0F);
        side1_8 = new ModelRenderer(this, 8, 6);
        side1_8.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1_8.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side1_8, 0.0F, -0.7853981633974483F, 0.0F);
        side2_7 = new ModelRenderer(this, 8, 9);
        side2_7.setRotationPoint(0.0F, 0.0F, 0.0F);
        side2_7.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side2_7, 0.0F, -1.5707963267948966F, 0.0F);
        side1_6 = new ModelRenderer(this, 8, 6);
        side1_6.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1_6.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side1_6, 0.0F, -2.356194490192345F, 0.0F);
        angledRing5 = new ModelRenderer(this, 0, 4);
        angledRing5.setRotationPoint(0.0F, 0.0F, 0.0F);
        angledRing5.addBox(-1.5F, 0.0F, 4.43F, 3, 1, 1, 0.0F);
        setRotateAngle(angledRing5, -0.6702064327658225F, 2.356194490192345F, 0.0F);
        side1_4 = new ModelRenderer(this, 8, 6);
        side1_4.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1_4.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side1_4, 0.0F, 2.356194490192345F, 0.0F);
        bottom1 = new ModelRenderer(this, 0, 13);
        bottom1.setRotationPoint(0.0F, 6.1F, 0.0F);
        bottom1.addBox(-1.5F, 0.0F, 1.62F, 3, 2, 2, 0.0F);
        side3_5 = new ModelRenderer(this, 0, 17);
        side3_5.setRotationPoint(0.0F, 0.0F, 0.0F);
        side3_5.addBox(-1.5F, -2.0F, 0.62F, 3, 2, 3, 0.0F);
        setRotateAngle(side3_5, 0.0F, 3.141592653589793F, 0.0F);
        secondaryTopRing1 = new ModelRenderer(this, 0, 2);
        secondaryTopRing1.setRotationPoint(0.0F, 0.7F, 0.0F);
        secondaryTopRing1.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        side4_5 = new ModelRenderer(this, 8, 12);
        side4_5.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4_5.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side4_5, 0.0F, 3.141592653589793F, 0.0F);
        side1_5 = new ModelRenderer(this, 8, 6);
        side1_5.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1_5.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side1_5, 0.0F, 3.141592653589793F, 0.0F);
        side4_7 = new ModelRenderer(this, 8, 12);
        side4_7.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4_7.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side4_7, 0.0F, -1.5707963267948966F, 0.0F);
        middleRing4 = new ModelRenderer(this, 0, 6);
        middleRing4.setRotationPoint(0.0F, 0.0F, 0.0F);
        middleRing4.addBox(-1.5F, 0.0F, 2.62F, 3, 4, 1, 0.0F);
        setRotateAngle(middleRing4, 0.0F, 2.356194490192345F, 0.0F);
        cap6 = new ModelRenderer(this, 8, 0);
        cap6.setRotationPoint(0.0F, 0.0F, 0.0F);
        cap6.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(cap6, 0.0F, -2.356194490192345F, 0.0F);
        angledRing1 = new ModelRenderer(this, 0, 10);
        angledRing1.setRotationPoint(0.0F, -3.03F, 0.0F);
        angledRing1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        cap2 = new ModelRenderer(this, 8, 0);
        cap2.setRotationPoint(0.0F, 0.0F, 0.0F);
        cap2.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(cap2, 0.0F, 0.7853981633974483F, 0.0F);
        secondaryTopRing7 = new ModelRenderer(this, 0, 2);
        secondaryTopRing7.setRotationPoint(0.0F, 0.0F, 0.0F);
        secondaryTopRing7.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(secondaryTopRing7, 0.0F, -1.5707963267948966F, 0.0F);
        bottom7 = new ModelRenderer(this, 0, 13);
        bottom7.setRotationPoint(0.0F, 0.0F, 0.0F);
        bottom7.addBox(-1.5F, 0.0F, 1.62F, 3, 2, 2, 0.0F);
        setRotateAngle(bottom7, 0.0F, -1.5707963267948966F, 0.0F);
        lowerRing1 = new ModelRenderer(this, 0, 11);
        lowerRing1.setRotationPoint(0.0F, 5.6F, 0.0F);
        lowerRing1.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        topRing8 = new ModelRenderer(this, 0, 0);
        topRing8.setRotationPoint(0.0F, 0.0F, 0.0F);
        topRing8.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(topRing8, 0.0F, -0.7853981633974483F, 0.0F);
        side4_3 = new ModelRenderer(this, 8, 12);
        side4_3.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4_3.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side4_3, 0.0F, 1.5707963267948966F, 0.0F);
        topRing5 = new ModelRenderer(this, 0, 0);
        topRing5.setRotationPoint(0.0F, 0.0F, 0.0F);
        topRing5.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(topRing5, 0.0F, 3.141592653589793F, 0.0F);
        side4_2 = new ModelRenderer(this, 8, 12);
        side4_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4_2.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side4_2, 0.0F, 0.7853981633974483F, 0.0F);
        middleRing7 = new ModelRenderer(this, 0, 6);
        middleRing7.setRotationPoint(0.0F, 0.0F, 0.0F);
        middleRing7.addBox(-1.5F, 0.0F, 2.62F, 3, 4, 1, 0.0F);
        setRotateAngle(middleRing7, 0.0F, -1.5707963267948966F, 0.0F);
        lowerRing6 = new ModelRenderer(this, 0, 11);
        lowerRing6.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerRing6.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(lowerRing6, 0.0F, -2.356194490192345F, 0.0F);
        angledRing3 = new ModelRenderer(this, 0, 4);
        angledRing3.setRotationPoint(0.0F, 0.0F, 0.0F);
        angledRing3.addBox(-1.5F, 0.0F, 4.43F, 3, 1, 1, 0.0F);
        setRotateAngle(angledRing3, -0.6702064327658225F, 0.7853981633974483F, 0.0F);
        topRing7 = new ModelRenderer(this, 0, 0);
        topRing7.setRotationPoint(0.0F, 0.0F, 0.0F);
        topRing7.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(topRing7, 0.0F, -1.5707963267948966F, 0.0F);
        secondaryTopRing2 = new ModelRenderer(this, 0, 2);
        secondaryTopRing2.setRotationPoint(0.0F, 0.0F, 0.0F);
        secondaryTopRing2.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(secondaryTopRing2, 0.0F, 0.7853981633974483F, 0.0F);
        bottom6 = new ModelRenderer(this, 0, 13);
        bottom6.setRotationPoint(0.0F, 0.0F, 0.0F);
        bottom6.addBox(-1.5F, 0.0F, 1.62F, 3, 2, 2, 0.0F);
        setRotateAngle(bottom6, 0.0F, -2.356194490192345F, 0.0F);
        side2_5 = new ModelRenderer(this, 8, 9);
        side2_5.setRotationPoint(0.0F, 0.0F, 0.0F);
        side2_5.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side2_5, 0.0F, 3.141592653589793F, 0.0F);
        side2_8 = new ModelRenderer(this, 8, 9);
        side2_8.setRotationPoint(0.0F, 0.0F, 0.0F);
        side2_8.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side2_8, 0.0F, -0.7853981633974483F, 0.0F);
        angledRing7 = new ModelRenderer(this, 0, 4);
        angledRing7.setRotationPoint(0.0F, 0.0F, 0.0F);
        angledRing7.addBox(-1.5F, 0.0F, 4.43F, 3, 1, 1, 0.0F);
        setRotateAngle(angledRing7, -0.6702064327658225F, -2.356194490192345F, 0.0F);
        lowerRing3 = new ModelRenderer(this, 0, 11);
        lowerRing3.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerRing3.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(lowerRing3, 0.0F, 1.5707963267948966F, 0.0F);
        lowerRing2 = new ModelRenderer(this, 0, 11);
        lowerRing2.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerRing2.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(lowerRing2, 0.0F, 0.7853981633974483F, 0.0F);
        side3_1 = new ModelRenderer(this, 0, 22);
        side3_1.setRotationPoint(4.2F, 1.5F, 0.0F);
        side3_1.addBox(-1.5F, -2.0F, -1.4F, 3, 2, 5, 0.0F);
        setRotateAngle(side3_1, 1.5707963267948966F, -1.5707963267948966F, 0.0F);
        side3_7 = new ModelRenderer(this, 0, 17);
        side3_7.setRotationPoint(0.0F, 0.0F, 0.0F);
        side3_7.addBox(-1.5F, -2.0F, 0.62F, 3, 2, 3, 0.0F);
        setRotateAngle(side3_7, 0.0F, -1.5707963267948966F, 0.0F);
        side3_8 = new ModelRenderer(this, 0, 17);
        side3_8.setRotationPoint(0.0F, 0.0F, 0.0F);
        side3_8.addBox(-1.5F, -2.0F, 0.62F, 3, 2, 3, 0.0F);
        setRotateAngle(side3_8, 0.0F, -0.7853981633974483F, 0.0F);
        secondaryTopRing3 = new ModelRenderer(this, 0, 2);
        secondaryTopRing3.setRotationPoint(0.0F, 0.0F, 0.0F);
        secondaryTopRing3.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(secondaryTopRing3, 0.0F, 1.5707963267948966F, 0.0F);
        middleRing2 = new ModelRenderer(this, 0, 6);
        middleRing2.setRotationPoint(0.0F, 0.0F, 0.0F);
        middleRing2.addBox(-1.5F, 0.0F, 2.62F, 3, 4, 1, 0.0F);
        setRotateAngle(middleRing2, 0.0F, 0.7853981633974483F, 0.0F);
        side3_4 = new ModelRenderer(this, 0, 17);
        side3_4.setRotationPoint(0.0F, 0.0F, 0.0F);
        side3_4.addBox(-1.5F, -2.0F, 0.62F, 3, 2, 3, 0.0F);
        setRotateAngle(side3_4, 0.0F, 2.356194490192345F, 0.0F);
        secondaryTopRing8 = new ModelRenderer(this, 0, 2);
        secondaryTopRing8.setRotationPoint(0.0F, 0.0F, 0.0F);
        secondaryTopRing8.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(secondaryTopRing8, 0.0F, -0.7853981633974483F, 0.0F);
        angledRing2 = new ModelRenderer(this, 0, 4);
        angledRing2.setRotationPoint(0.0F, 0.0F, 0.0F);
        angledRing2.addBox(-1.5F, 0.0F, 4.43F, 3, 1, 1, 0.0F);
        setRotateAngle(angledRing2, -0.6702064327658225F, 0.0F, 0.0F);
        middleRing5 = new ModelRenderer(this, 0, 6);
        middleRing5.setRotationPoint(0.0F, 0.0F, 0.0F);
        middleRing5.addBox(-1.5F, 0.0F, 2.62F, 3, 4, 1, 0.0F);
        setRotateAngle(middleRing5, 0.0F, 3.141592653589793F, 0.0F);
        cap7 = new ModelRenderer(this, 8, 0);
        cap7.setRotationPoint(0.0F, 0.0F, 0.0F);
        cap7.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(cap7, 0.0F, -1.5707963267948966F, 0.0F);
        lowerRing7 = new ModelRenderer(this, 0, 11);
        lowerRing7.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerRing7.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(lowerRing7, 0.0F, -1.5707963267948966F, 0.0F);
        lowerRing5 = new ModelRenderer(this, 0, 11);
        lowerRing5.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerRing5.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(lowerRing5, 0.0F, 3.141592653589793F, 0.0F);
        lowerRing8 = new ModelRenderer(this, 0, 11);
        lowerRing8.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerRing8.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(lowerRing8, 0.0F, -0.7853981633974483F, 0.0F);
        middleRing1 = new ModelRenderer(this, 0, 6);
        middleRing1.setRotationPoint(0.0F, 2.48F, 0.0F);
        middleRing1.addBox(-1.5F, 0.0F, 2.62F, 3, 4, 1, 0.0F);
        secondaryTopRing5 = new ModelRenderer(this, 0, 2);
        secondaryTopRing5.setRotationPoint(0.0F, 0.0F, 0.0F);
        secondaryTopRing5.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(secondaryTopRing5, 0.0F, 3.141592653589793F, 0.0F);
        angledRing9 = new ModelRenderer(this, 0, 4);
        angledRing9.setRotationPoint(0.0F, 0.0F, 0.0F);
        angledRing9.addBox(-1.5F, 0.0F, 4.43F, 3, 1, 1, 0.0F);
        setRotateAngle(angledRing9, -0.6702064327658225F, -0.7853981633974483F, 0.0F);
        side3_2 = new ModelRenderer(this, 0, 17);
        side3_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        side3_2.addBox(-1.5F, -2.0F, 0.62F, 3, 2, 3, 0.0F);
        setRotateAngle(side3_2, 0.0F, 0.7853981633974483F, 0.0F);
        topRing3 = new ModelRenderer(this, 0, 0);
        topRing3.setRotationPoint(0.0F, 0.0F, 0.0F);
        topRing3.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(topRing3, 0.0F, 1.5707963267948966F, 0.0F);
        bottom2 = new ModelRenderer(this, 0, 13);
        bottom2.setRotationPoint(0.0F, 0.0F, 0.0F);
        bottom2.addBox(-1.5F, 0.0F, 1.62F, 3, 2, 2, 0.0F);
        setRotateAngle(bottom2, 0.0F, 0.7853981633974483F, 0.0F);
        side3_6 = new ModelRenderer(this, 0, 17);
        side3_6.setRotationPoint(0.0F, 0.0F, 0.0F);
        side3_6.addBox(-1.5F, -2.0F, 0.62F, 3, 2, 3, 0.0F);
        setRotateAngle(side3_6, 0.0F, -2.356194490192345F, 0.0F);
        side3_3 = new ModelRenderer(this, 0, 17);
        side3_3.setRotationPoint(0.0F, 0.0F, 0.0F);
        side3_3.addBox(-1.5F, -2.0F, 0.62F, 3, 2, 3, 0.0F);
        setRotateAngle(side3_3, 0.0F, 1.5707963267948966F, 0.0F);
        cap3 = new ModelRenderer(this, 8, 0);
        cap3.setRotationPoint(0.0F, 0.0F, 0.0F);
        cap3.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(cap3, 0.0F, 1.5707963267948966F, 0.0F);
        angledRing8 = new ModelRenderer(this, 0, 4);
        angledRing8.setRotationPoint(0.0F, 0.0F, 0.0F);
        angledRing8.addBox(-1.5F, 0.0F, 4.43F, 3, 1, 1, 0.0F);
        setRotateAngle(angledRing8, -0.6702064327658225F, -1.5707963267948966F, 0.0F);
        angledRing4 = new ModelRenderer(this, 0, 4);
        angledRing4.setRotationPoint(0.0F, 0.0F, 0.0F);
        angledRing4.addBox(-1.5F, 0.0F, 4.43F, 3, 1, 1, 0.0F);
        setRotateAngle(angledRing4, -0.6702064327658225F, 1.5707963267948966F, 0.0F);
        bottom8 = new ModelRenderer(this, 0, 13);
        bottom8.setRotationPoint(0.0F, 0.0F, 0.0F);
        bottom8.addBox(-1.5F, 0.0F, 1.62F, 3, 2, 2, 0.0F);
        setRotateAngle(bottom8, 0.0F, -0.7853981633974483F, 0.0F);
        side2_3 = new ModelRenderer(this, 8, 9);
        side2_3.setRotationPoint(0.0F, 0.0F, 0.0F);
        side2_3.addBox(-1.5F, -2.0F, 2.62F, 3, 2, 1, 0.0F);
        setRotateAngle(side2_3, 0.0F, 1.5707963267948966F, 0.0F);
        angledRing6 = new ModelRenderer(this, 0, 4);
        angledRing6.setRotationPoint(0.0F, 0.0F, 0.0F);
        angledRing6.addBox(-1.5F, 0.0F, 4.43F, 3, 1, 1, 0.0F);
        setRotateAngle(angledRing6, -0.6702064327658225F, 3.141592653589793F, 0.0F);
        middleRing3 = new ModelRenderer(this, 0, 6);
        middleRing3.setRotationPoint(0.0F, 0.0F, 0.0F);
        middleRing3.addBox(-1.5F, 0.0F, 2.62F, 3, 4, 1, 0.0F);
        setRotateAngle(middleRing3, 0.0F, 1.5707963267948966F, 0.0F);
        bottom4 = new ModelRenderer(this, 0, 13);
        bottom4.setRotationPoint(0.0F, 0.0F, 0.0F);
        bottom4.addBox(-1.5F, 0.0F, 1.62F, 3, 2, 2, 0.0F);
        setRotateAngle(bottom4, 0.0F, 2.356194490192345F, 0.0F);
        bottom3 = new ModelRenderer(this, 0, 13);
        bottom3.setRotationPoint(0.0F, 0.0F, 0.0F);
        bottom3.addBox(-1.5F, 0.0F, 1.62F, 3, 2, 2, 0.0F);
        setRotateAngle(bottom3, 0.0F, 1.5707963267948966F, 0.0F);
        cap4 = new ModelRenderer(this, 8, 0);
        cap4.setRotationPoint(0.0F, 0.0F, 0.0F);
        cap4.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(cap4, 0.0F, 2.356194490192345F, 0.0F);
        topRing1.addChild(topRing6);
        side1_1.addChild(side1_7);
        side4_1.addChild(side4_6);
        side4_1.addChild(side4_8);
        middleRing1.addChild(middleRing6);
        side4_1.addChild(side4_4);
        side2_1.addChild(side2_4);
        secondaryTopRing1.addChild(secondaryTopRing6);
        bottom1.addChild(bottom5);
        side1_1.addChild(side1_3);
        side2_1.addChild(side2_2);
        side2_1.addChild(side2_6);
        middleRing1.addChild(middleRing8);
        topRing1.addChild(topRing4);
        cap1.addChild(cap8);
        secondaryTopRing1.addChild(secondaryTopRing4);
        topRing1.addChild(topRing2);
        cap1.addChild(cap5);
        side1_1.addChild(side1_2);
        lowerRing1.addChild(lowerRing4);
        side1_1.addChild(side1_8);
        side2_1.addChild(side2_7);
        side1_1.addChild(side1_6);
        angledRing1.addChild(angledRing5);
        side1_1.addChild(side1_4);
        side3_1.addChild(side3_5);
        side4_1.addChild(side4_5);
        side1_1.addChild(side1_5);
        side4_1.addChild(side4_7);
        middleRing1.addChild(middleRing4);
        cap1.addChild(cap6);
        cap1.addChild(cap2);
        secondaryTopRing1.addChild(secondaryTopRing7);
        bottom1.addChild(bottom7);
        topRing1.addChild(topRing8);
        side4_1.addChild(side4_3);
        topRing1.addChild(topRing5);
        side4_1.addChild(side4_2);
        middleRing1.addChild(middleRing7);
        lowerRing1.addChild(lowerRing6);
        angledRing1.addChild(angledRing3);
        topRing1.addChild(topRing7);
        secondaryTopRing1.addChild(secondaryTopRing2);
        bottom1.addChild(bottom6);
        side2_1.addChild(side2_5);
        side2_1.addChild(side2_8);
        angledRing1.addChild(angledRing7);
        lowerRing1.addChild(lowerRing3);
        lowerRing1.addChild(lowerRing2);
        side3_1.addChild(side3_7);
        side3_1.addChild(side3_8);
        secondaryTopRing1.addChild(secondaryTopRing3);
        middleRing1.addChild(middleRing2);
        side3_1.addChild(side3_4);
        secondaryTopRing1.addChild(secondaryTopRing8);
        angledRing1.addChild(angledRing2);
        middleRing1.addChild(middleRing5);
        cap1.addChild(cap7);
        lowerRing1.addChild(lowerRing7);
        lowerRing1.addChild(lowerRing5);
        lowerRing1.addChild(lowerRing8);
        secondaryTopRing1.addChild(secondaryTopRing5);
        angledRing1.addChild(angledRing9);
        side3_1.addChild(side3_2);
        topRing1.addChild(topRing3);
        bottom1.addChild(bottom2);
        side3_1.addChild(side3_6);
        side3_1.addChild(side3_3);
        cap1.addChild(cap3);
        angledRing1.addChild(angledRing8);
        angledRing1.addChild(angledRing4);
        bottom1.addChild(bottom8);
        side2_1.addChild(side2_3);
        angledRing1.addChild(angledRing6);
        middleRing1.addChild(middleRing3);
        bottom1.addChild(bottom4);
        bottom1.addChild(bottom3);
        cap1.addChild(cap4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(cap1.offsetX, cap1.offsetY, cap1.offsetZ);
        GL11.glTranslatef(cap1.rotationPointX * f5, cap1.rotationPointY * f5, cap1.rotationPointZ * f5);
        GL11.glScaled(0.4D, 0.4D, 0.4D);
        GL11.glTranslatef(-cap1.offsetX, -cap1.offsetY, -cap1.offsetZ);
        GL11.glTranslatef(-cap1.rotationPointX * f5, -cap1.rotationPointY * f5, -cap1.rotationPointZ * f5);
        cap1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(topRing1.offsetX, topRing1.offsetY, topRing1.offsetZ);
        GL11.glTranslatef(topRing1.rotationPointX * f5, topRing1.rotationPointY * f5, topRing1.rotationPointZ * f5);
        GL11.glScaled(1.05D, 0.7D, 1.05D);
        GL11.glTranslatef(-topRing1.offsetX, -topRing1.offsetY, -topRing1.offsetZ);
        GL11.glTranslatef(-topRing1.rotationPointX * f5, -topRing1.rotationPointY * f5, -topRing1.rotationPointZ * f5);
        topRing1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(side2_1.offsetX, side2_1.offsetY, side2_1.offsetZ);
        GL11.glTranslatef(side2_1.rotationPointX * f5, side2_1.rotationPointY * f5, side2_1.rotationPointZ * f5);
        GL11.glScaled(0.25D, 0.25D, 0.25D);
        GL11.glTranslatef(-side2_1.offsetX, -side2_1.offsetY, -side2_1.offsetZ);
        GL11.glTranslatef(-side2_1.rotationPointX * f5, -side2_1.rotationPointY * f5, -side2_1.rotationPointZ * f5);
        side2_1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(side4_1.offsetX, side4_1.offsetY, side4_1.offsetZ);
        GL11.glTranslatef(side4_1.rotationPointX * f5, side4_1.rotationPointY * f5, side4_1.rotationPointZ * f5);
        GL11.glScaled(0.23D, 0.23D, 0.23D);
        GL11.glTranslatef(-side4_1.offsetX, -side4_1.offsetY, -side4_1.offsetZ);
        GL11.glTranslatef(-side4_1.rotationPointX * f5, -side4_1.rotationPointY * f5, -side4_1.rotationPointZ * f5);
        side4_1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(side1_1.offsetX, side1_1.offsetY, side1_1.offsetZ);
        GL11.glTranslatef(side1_1.rotationPointX * f5, side1_1.rotationPointY * f5, side1_1.rotationPointZ * f5);
        GL11.glScaled(0.3D, 0.3D, 0.3D);
        GL11.glTranslatef(-side1_1.offsetX, -side1_1.offsetY, -side1_1.offsetZ);
        GL11.glTranslatef(-side1_1.rotationPointX * f5, -side1_1.rotationPointY * f5, -side1_1.rotationPointZ * f5);
        side1_1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(bottom1.offsetX, bottom1.offsetY, bottom1.offsetZ);
        GL11.glTranslatef(bottom1.rotationPointX * f5, bottom1.rotationPointY * f5, bottom1.rotationPointZ * f5);
        GL11.glScaled(0.7D, 0.7D, 0.7D);
        GL11.glTranslatef(-bottom1.offsetX, -bottom1.offsetY, -bottom1.offsetZ);
        GL11.glTranslatef(-bottom1.rotationPointX * f5, -bottom1.rotationPointY * f5, -bottom1.rotationPointZ * f5);
        bottom1.render(f5);
        GL11.glPopMatrix();
        secondaryTopRing1.render(f5);
        GL11.glPushMatrix();
        GL11.glTranslatef(angledRing1.offsetX, angledRing1.offsetY, angledRing1.offsetZ);
        GL11.glTranslatef(angledRing1.rotationPointX * f5, angledRing1.rotationPointY * f5, angledRing1.rotationPointZ * f5);
        GL11.glScaled(0.85D, 1.4D, 0.85D);
        GL11.glTranslatef(-angledRing1.offsetX, -angledRing1.offsetY, -angledRing1.offsetZ);
        GL11.glTranslatef(-angledRing1.rotationPointX * f5, -angledRing1.rotationPointY * f5, -angledRing1.rotationPointZ * f5);
        angledRing1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(lowerRing1.offsetX, lowerRing1.offsetY, lowerRing1.offsetZ);
        GL11.glTranslatef(lowerRing1.rotationPointX * f5, lowerRing1.rotationPointY * f5, lowerRing1.rotationPointZ * f5);
        GL11.glScaled(0.9D, 0.7D, 0.9D);
        GL11.glTranslatef(-lowerRing1.offsetX, -lowerRing1.offsetY, -lowerRing1.offsetZ);
        GL11.glTranslatef(-lowerRing1.rotationPointX * f5, -lowerRing1.rotationPointY * f5, -lowerRing1.rotationPointZ * f5);
        lowerRing1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(side3_1.offsetX, side3_1.offsetY, side3_1.offsetZ);
        GL11.glTranslatef(side3_1.rotationPointX * f5, side3_1.rotationPointY * f5, side3_1.rotationPointZ * f5);
        GL11.glScaled(0.3D, 0.3D, 0.3D);
        GL11.glTranslatef(-side3_1.offsetX, -side3_1.offsetY, -side3_1.offsetZ);
        GL11.glTranslatef(-side3_1.rotationPointX * f5, -side3_1.rotationPointY * f5, -side3_1.rotationPointZ * f5);
        side3_1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(middleRing1.offsetX, middleRing1.offsetY, middleRing1.offsetZ);
        GL11.glTranslatef(middleRing1.rotationPointX * f5, middleRing1.rotationPointY * f5, middleRing1.rotationPointZ * f5);
        GL11.glScaled(0.85D, 0.85D, 0.85D);
        GL11.glTranslatef(-middleRing1.offsetX, -middleRing1.offsetY, -middleRing1.offsetZ);
        GL11.glTranslatef(-middleRing1.rotationPointX * f5, -middleRing1.rotationPointY * f5, -middleRing1.rotationPointZ * f5);
        middleRing1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
