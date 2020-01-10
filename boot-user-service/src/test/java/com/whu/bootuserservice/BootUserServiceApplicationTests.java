package com.whu.bootuserservice;

import com.alibaba.fastjson.JSON;
import com.whu.service.UserService;
import com.whu.service.impl.AdminServiceImpl;
import com.whu.spatialIndex.gridIndex.GridIndex;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BootUserServiceApplicationTests {

    @Test
    void contextLoads1() {

    }

//    @Test
//    void contextLoads() {
//        String s = "1,2,3,4,";
//        String[] ll = s.split(",");
//        for(int i=0; i<ll.length ;i++){
//            System.out.println(ll[i]);
//        }
        //X方向上的起始坐标
//        double xStart=72.0;
//        //Y方向上的起始坐标
//        double yStart=2;
//        double xEnd=136.0;
//        double yEnd=54.0;
//        double dx=0.05;//
//        double dy=0.05;//
//        GridIndex gridIndex=new GridIndex();
//        gridIndex.createGridIndex(xStart,yStart,xEnd,yEnd,dx,dy);
//        gridIndex.point2GridObject(73.6,3.1);
//        gridIndex.point2GridObject(72.2,4.1);
//        gridIndex.point2GridObject(74.7,5.4);
//        gridIndex.point2GridObject(75.7,6.1);
//        String objectToJson = JSON.toJSONString(gridIndex.getArrGrids());
//        System.out.println(objectToJson);
//        logger.info(objectToJson);

//    }

//    @MockBean
//    private OrderEntryRepository orderEntryRepository;
//    private UserService userService;

//    @Before
//    public void setUp()
//    {
////        orderEntryRepository= Mockito.mock(OrderEntryRepository.class);
//        userService=new AdminServiceImpl();
//    }
//    private OrderEntry orderEntry=new OrderEntry();
//
//    @Test
//    public void findByIdTest()
//    {
//        Mockito.when(orderEntryRepository.findOne(Mockito.anyLong())).thenReturn(orderEntry);
//        OrderEntry entry=service.findById(new Long(1));
//        assertEquals(entry.getId(),orderEntry.getId());
//    }

//    @Test
//    public void updateEntryTest() throws Exception
//    {
//        final String orderEntryJson = IOUtils.toString(this.getClass().getResourceAsStream("/static/meta-data/orderentry-example.json"),
//                CharEncoding.UTF_8
//        );
//        ObjectMapper mapper= ObjectMapperFactory.getInstance();
//
//        Mockito.when(orderEntryRepository.getOne(Mockito.anyLong())).thenReturn(orderEntry);
//        Mockito.doAnswer(returnsFirstArg()).when(orderEntryRepository).save(Mockito.any(OrderEntry.class));
//        OrderEntry entry =service.update(new Long(1),mapper.readTree(orderJson));
//        assertEquals(new Long(2),entry.getQuantity());
//        assertEquals(new Double(10),entry.getUnitPrice());
//    }

}
