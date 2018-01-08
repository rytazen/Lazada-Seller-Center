import com.alibaba.normandie.api.core.LazadaClient;
import com.alibaba.normandie.api.core.exception.LazadaException;
import com.alibaba.normandie.api.endpoint.product.model.Category;
import com.alibaba.normandie.api.endpoint.product.request.GetCategoryTree;
import com.alibaba.normandie.api.endpoint.product.response.GetCategoryTreeResponse;
import config.Config;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        LazadaClient.init(Config.url, Config.userId, Config.apiKey);
        //System.out.println(LazadaClient.getDefaultClient());
        GetCategories();
    }

    public static void GetCategories() {
        GetCategoryTree getCategoryTree = new GetCategoryTree();

        try {
            GetCategoryTreeResponse response = getCategoryTree.execute();
            List<Category> categories = response.getBody();
            for (Category category : categories) {
                //System.out.println(category.getChildren());
                for (Category leaf : category.getChildren()) {
                    //System.out.println(leaf.getCategoryId()+" "+leaf.getName());
                    for (Category childrenOfLeaves : leaf.getChildren()) {
                        //System.out.println(childrenOfLeaves.getCategoryId()+" "+childrenOfLeaves.getName());
                        for (Category grandChild : childrenOfLeaves.getChildren()) {
                            System.out.println(grandChild.getCategoryId() + " " + grandChild.getName());
                        }
                    }
                }
            }
        } catch (LazadaException e) {
            System.out.println(e.getResponseStr());
        }
    }
}