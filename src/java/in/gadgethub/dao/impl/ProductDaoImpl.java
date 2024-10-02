/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.ProductDao;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDutil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sandeepkumar
 */
public class ProductDaoImpl implements ProductDao {

    

    @Override
    public String addProduct(ProductPojo product) {
        String status = "Product registration Failed!";
        if(product.getProdId() == null){
            product.setProdId(IDutil.generateProdId());
        }
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("Insert into users values(?,?,?,?,?,?,?)");
            ps.setString(1,product.getProdId());
            ps.setString(2,product.getProdName());
            ps.setString(3,product.getProdType());
            ps.setString(4,product.getProdInfo());
            ps.setDouble(5,product.getProdPrice());
            ps.setInt(6,product.getProdQuantity());
            ps.setBlob(7, product.getProdimage());
            ps.setString(8, "Y");
            int count = ps.executeUpdate();
            if(count==1){
                status = "Product added successfully with Id:"+product.getProdId();
                
            }   
        }
        catch(SQLException ex){
            System.out.println("Error in addProduct:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public String updateProduct(ProductPojo prevProduct, ProductPojo updatedProduct) {
        String status = "Updation Failed!";
        if(!prevProduct.getProdId().equals(updatedProduct.getProdId())){
            status = "Product ID's Do Not Match. Updation Failed!";
            return status;
        }
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("Update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=?,pimage=? where pid=?");
           
            ps.setString(1,updatedProduct.getProdName());
            ps.setString(2,updatedProduct.getProdType());
            ps.setString(3,updatedProduct.getProdInfo());
            ps.setDouble(4,updatedProduct.getProdPrice());
            ps.setInt(5,updatedProduct.getProdQuantity());
            ps.setBlob(6, updatedProduct.getProdimage());
            ps.setString(7,updatedProduct.getProdId());
            int count = ps.executeUpdate();
            if(count==1){
                status = "Product updated successfully";
                
            }   
        }
        catch(SQLException ex){
            System.out.println("Error in updateProduct:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public String updateProductPrice(String prodId, double updatedPrice) {
        String status = "Updation Failed!";
        
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("Update products set pprice=? where pid=?");
           
            ps.setDouble(1,updatedPrice);
            ps.setString(2,prodId);
            int count = ps.executeUpdate();
            if(count==1){
                status = "Product updated successfully";
            }   
        }
        catch(SQLException ex){
            System.out.println("Error in updateProductPrice:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public List<ProductPojo> getAllProducts() {
        List<ProductPojo> productList = new ArrayList();
        Connection conn = DBUtil.provideConnection();
        Statement st = null;
        ResultSet rs = null;
        try{
            st = conn.createStatement();
            rs = st.executeQuery("Select * from Products where available = 'Y' ");
            while(rs.next()){
                ProductPojo product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdimage(rs.getAsciiStream("image"));
                productList.add(product);
            }
        }catch(SQLException ex){
            System.out.println("Error in getAllProducts:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(st);
        
        return productList;
    }

    @Override
    public List<ProductPojo> getAllProductsByType(String type) {
         List<ProductPojo> productList = new ArrayList();
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        type = type.toLowerCase();
        try{
            ps = conn.prepareStatement("Select * from products where lower(ptype) like ? and available = 'Y'");
            ps.setString(1,"%"+type+"%");
            rs = ps.executeQuery();
            while(rs.next()){
                ProductPojo product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdimage(rs.getAsciiStream("image"));
                productList.add(product);
            }
        }catch(SQLException ex){
            System.out.println("Error in getAllProducts:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        
        return productList;
    }

    @Override
    public List<ProductPojo> searchAllProducts(String search) {
        List<ProductPojo> productList = new ArrayList();
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        search = search.toLowerCase();
        try{
            ps = conn.prepareStatement("Select * from products where lower(ptype) like ? or lower(pname) like ? or lower(pinfo) like ? and available = 'Y'");
            ps.setString(1,"%"+search+"%");
            ps.setString(2,"%"+search+"%");
            ps.setString(3,"%"+search+"%");
            rs = ps.executeQuery();
            while(rs.next()){
                ProductPojo product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdimage(rs.getAsciiStream("image"));
                productList.add(product);
            }
        }catch(SQLException ex){
            System.out.println("Error in searchAllProducts:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        
        return productList;
    }

    @Override
    public ProductPojo getProductDetails(String prodId) {
        ProductPojo product = null;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("Select * from products where pid = ?");
            ps.setString(1, prodId);
            rs =ps.executeQuery();
            if(rs.next()){
                product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdimage(rs.getAsciiStream("image"));
                
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getProductDetails:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return product;
    }

    @Override
    public int getProductQuantity(String prodId) {
        int quantity = 0;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("Select pquantity from products where pid =?");
            ps.setString(1,prodId);
            rs = ps.executeQuery();
            if(rs.next()){
                quantity=rs.getInt(1);
            }   
        }
        catch(SQLException ex){
            System.out.println("Error in getProductQuantity:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return quantity;
    }

    @Override
    public String updateProductWithoutImage(String prevProductId, ProductPojo updatedProduct) {
        String status = "Updation Failed!";
        
        int prevQuantity = 0;
        if(!prevProductId.equals(updatedProduct.getProdId())){
            status = "Product ID's Do Not Match. Updation Failed!";
            return status;
        }
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try{
            
            prevQuantity = getProductQuantity(prevProductId);
            ps = conn.prepareStatement("Update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=? where pid=?");
           
            ps.setString(1,updatedProduct.getProdName());
            ps.setString(2,updatedProduct.getProdType());
            ps.setString(3,updatedProduct.getProdInfo());
            ps.setDouble(4,updatedProduct.getProdPrice());
            ps.setInt(5,updatedProduct.getProdQuantity());
            ps.setString(6,updatedProduct.getProdId());
            int count = ps.executeUpdate();
            if(count==1 && prevQuantity<updatedProduct.getProdQuantity()){
                status = "Product updated succesfully Mail sent";    
            }
            else if(count == 1){
                    status = "Product updated Successfully";
                    }
        }
        catch(SQLException ex){
            System.out.println("Error in updateProduct:"+ex);
            ex.printStackTrace();
        }
        
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public double getProductPrice(String prodId) {
        double price = 0.0;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("Select pprice from products where pid =?");
            ps.setString(1,prodId);
            rs = ps.executeQuery();
            if(rs.next()){
                price=rs.getDouble(1);
            }   
        }
        catch(SQLException ex){
            System.out.println("Error in getProductPrice:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return price;
    }

    @Override
    public Boolean sellNProduct(String prodId, int n) {
        boolean result = false;
        
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("Update products set pquantity=(pquantity-?) where pid=? and available = 'Y'");
           
            ps.setInt(1,n);
            ps.setString(2,prodId);
            int count = ps.executeUpdate();
            if(count==1){
               result = true;
            }   
        }
        catch(SQLException ex){
            System.out.println("Error in sellNProduct:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return result;
    }

    @Override
    public List<String> getAllProductsType() {
       List<String> productTypeList = new ArrayList();
        Connection conn = DBUtil.provideConnection();
        Statement st = null;
        ResultSet rs = null;
        
        try{
            st = conn.createStatement();
          
            rs = st.executeQuery("Select distinct ptype from products where available = 'Y'");
            while(rs.next()){
                productTypeList.add(rs.getString(1));
            }
        }catch(SQLException ex){
            System.out.println("Error in getAllProductsType:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(st);
        return productTypeList;
    }

    @Override
    public byte[] getImage(String prodId) {
        byte[]arr = null;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("Select image from products where pid =?");
            ps.setString(1,prodId);
            rs = ps.executeQuery();
            if(rs.next()){
                arr=rs.getBytes(1);
            }   
        }
        catch(SQLException ex){
            System.out.println("Error in getProductPrice:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return arr;
    }

    @Override
    public String removeProduct(String prodId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

