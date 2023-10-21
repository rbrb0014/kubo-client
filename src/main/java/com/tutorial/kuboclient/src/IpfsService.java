package com.tutorial.kuboclient.src;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class IpfsService {
    private final IPFS ipfs = new IPFS(new MultiAddress("/ip4/127.0.0.1/tcp/5001"));
    String[] createContents(MultipartFile mfile) throws IOException {
//        ipfs.refs.local();
//        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File("hello.txt")); //local file upload
        NamedStreamable.ByteArrayWrapper contents = new NamedStreamable.ByteArrayWrapper(mfile.getOriginalFilename(), mfile.getBytes());
        List<MerkleNode> files = ipfs.add(contents,true);
        return files.stream()
                .map(file-> file.hash +" "+ file.name+" "+file.links+" "+file.type)
                .toArray(String[]::new);
    }

    byte[] getContents(String cid) throws IOException {
        Multihash filePointer = Multihash.fromBase58(cid);

        return ipfs.cat(filePointer);
    }
}
