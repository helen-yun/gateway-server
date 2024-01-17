
#  Naver I/F

## 개발 환경
* URL : sandbox.api.naver.com
* Endponit Url : http://sandbox.api.naver.com/ShopN/AlphaSellerService41
* 서비스명 : AlphaSellerService41
* 판매자 ID : qa2tc354
* accessLicense: 0100010000c0af4d7d1b7869b67ef59960237f1cd5440b29ba897a17eb11aa6906f501daea
* secretkey: AQABAAAWY2bEvYe26ZkVlKKDBYRe4JLISGyvuRB2Qg4s4QaMsw==


## 개발서버
  - ssh user@211.177.136.90
    - user  //  Ncity20!
    - home : /home/user/gateway/naver

## 운영 환경

* URL : api.naver.com
* Endponit Url : http://api.naver.com/ShopN/SellerService41
* 서비스명 : SellerService41
* 판매자 ID : platfos0914
* accessLicense: 0100010000e420499c61d95d4872e85c162a4160256c393499fdf97a95fcb2c441840f7914
* secretkey: AQABAACVduH+javEfzAHGeEvkXi1dkcs5V56iU9VzbiNt7WIMw==




## WSDL

* 상품API :
* 주문API : StoreFarm_API_AlphaSellerService41_WSDL(sandbox)_20170124.zip
  - (주문정보 조회가 필요하실 경우, VPN계정을 통해 상품상세페이지에 접근하시어 주문생성이 선행되어야 한다)
* ServiceName Operation별로 차이: StoreFarm-API_Reference-ko.pdf 파일참고


## 대상 API
* 상품 카테고리 리스트 조회   GetAllCategoryList
* 상품 리스트 조회          GetProductList
* 상품 상세 조회           GetProduct
* 상품 등록               ManageProduct
* 상품 수정               ManageProduct
* 상품 삭제               DeleteProduct

거래 리스트 조회
거래 상세 조회
상품 판매 승인 요청
상품 판매 승인 취소 요청


## ASIX2 빌드
/Users/dplee/code/workspace/util/axis2-1.7.9


export AXIS2_HOME=/Users/dplee/code/workspace/util/axis2-1.7.9
export PATH="$PATH:$AXIS2_HOME/bin"
export JAVA_HOME=/library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home

// product
/Users/dplee/code/workspace/util/axis2-1.7.9/bin/wsdl2java.sh -uri /Users/dplee/code/workspace/api-naver-asix2/wsdl/production/SmartStore_API_CustomerInquiry/CustomerInquiryService.wsdl -ap -o client_production
/Users/dplee/code/workspace/util/axis2-1.7.9/bin/wsdl2java.sh -uri /Users/dplee/code/workspace/api-naver-asix2/wsdl/production/SmartStore_API_Product/ShopNAPI.wsdl -ap -o client_production
/Users/dplee/code/workspace/util/axis2-1.7.9/bin/wsdl2java.sh -uri /Users/dplee/code/workspace/api-naver-asix2/wsdl/production/SmartStore_API_SellerService41/SellerService.wsdl -ap -o client_production
/Users/dplee/code/workspace/util/axis2-1.7.9/bin/wsdl2java.sh -uri /Users/dplee/code/workspace/api-naver-asix2/wsdl/production/SmartStore_API_Store/StoreAPI.wsdl -ap -o client_production

// sandbox
/Users/dplee/code/workspace/util/axis2-1.7.9/bin/wsdl2java.sh -uri /Users/dplee/code/workspace/api-naver-asix2/wsdl/sandbox/SmartStore_API_CustomerInquiry/CustomerInquiryService.wsdl -ap -o client_sandbox
/Users/dplee/code/workspace/util/axis2-1.7.9/bin/wsdl2java.sh -uri /Users/dplee/code/workspace/api-naver-asix2/wsdl/sandbox/SmartStore_API_Product/ShopNAPI.wsdl -ap -o client_sandbox
/Users/dplee/code/workspace/util/axis2-1.7.9/bin/wsdl2java.sh -uri /Users/dplee/code/workspace/api-naver-asix2/wsdl/sandbox/SmartStore_API_SellerService41/SellerService.wsdl -ap -o client_sandbox
/Users/dplee/code/workspace/util/axis2-1.7.9/bin/wsdl2java.sh -uri /Users/dplee/code/workspace/api-naver-asix2/wsdl/sandbox/SmartStore_API_Store/StoreAPI.wsdl -ap -o client_sandbox




/Users/dplee/code/workspace/util/axis2-1.7.9/bin/wsdl2java.sh -uri /Users/dplee/code/workspace/api-auction/wsdl/ShoppingService.wsdl -ap -o client


export JAVA_HOME=/library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home
export PATH="$PATH:$HOME=/library/Java/JavaVirtualMachines/jdk1.8.0
