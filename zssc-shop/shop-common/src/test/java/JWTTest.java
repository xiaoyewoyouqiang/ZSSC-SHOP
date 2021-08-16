import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.Test;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTTest {

    @Test
    public void testJWTHello(){

        // 创建一个JWTbuilder
        JWTCreator.Builder builder = JWT.create();

        Map<String,Object> headMap = new HashMap<>();
        headMap.put("key","value");

        // 设置头信息【第一部分】
        builder.withHeader(headMap);


        // 设置负载(用户的数据可以放在负载)【第二部分】
        builder.withClaim("name","admin");
        builder.withClaim("age",20);
        builder.withClaim("sal",2000.0);

        // 给Token设置一个有效时间
        Calendar calendar = Calendar.getInstance(); // 后去日历类
        calendar.add(Calendar.SECOND,3); // 超时时间是30s
        builder.withExpiresAt(calendar.getTime());

        // 设置签名【第三部】
        String token = builder.sign(Algorithm.HMAC256("2008-shop"));// 盐值

        System.out.println(token);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        testCheck(token);
    }

//    @Test
    public void testCheck(String token){

        // 用户提交的token
//        String toke = "eyJhbGciOiJIUzI1NiIsImtleSI6InZhbHVlIiwidHlwIjoiSldUIn0.eyJuYW1lIjoiYWRtaW4iLCJpYXQiOjE2MTIyNTI0NjYsImFnZSI6MjAsInNhbCI6MjAwMC4wfQ.BJUMhidd5aI8wfouYTWm7GbDsjX5HO5VTRvehPabcd4";

        // 获取JWT的校验对象
        JWTVerifier build = JWT.require(Algorithm.HMAC256("2008-shop")).build();

        // 校验
        DecodedJWT verify = build.verify(token); // 如果token校验失败，这个方法会抛出异常
        System.out.println("token校验成功。。。。");

        // 获取tokne中的内容
        String name = verify.getClaim("name").asString(); // 需要按照参数的类型调用不同的方法来获取，否则获取不到植
        System.out.println("name:"+name);
        int age= verify.getClaim("age").asInt();
        System.out.println("age:"+age);

        double sal = verify.getClaim("sal").asDouble();
        System.out.println("sal:"+sal);

    }
}
