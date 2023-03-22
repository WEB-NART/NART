// https://docs.cypress.io/api/introduction/api.html
describe("Integral Test", () => {
  it("Login Test", () => {
    cy.visit("http://localhost:4173/login");

    cy.get('input[name="username"]').type("tony");
    cy.get('input[name="password"]').type("123456");
    cy.get('button[type="submit"]').click();
    cy.get('button[type="submit"]').click();
    cy.get("img").should(
      "have.attr",
      "src",
      "https://s1.ax1x.com/2023/03/12/ppKvjns.png"
    );
  });
  it("allows a user to send a message to a friend", () => {
    cy.get('input[id="searchMyFriend"]').type("mike");
    cy.get('button[id="searchmf"]').click();
    cy.get("span").should("contain", "mike");

    cy.contains('span', 'Chat').click();
    cy.get("div").should("contain", "hi");

    cy.get("textarea").type("Hello, how are you?");
    cy.contains("span", "Send").click();
    cy.get("div").should("contain", "Hello, how are you?");
  });

  it("send image test", () => {
    cy.get('div[class="el-upload el-upload--text"]').click();
    cy.fixture("example.png", "base64").then((fileContent) => {
      const file = new File([fileContent], "example.png", {
        type: "image/png",
      });
      cy.get('div[class="el-upload el-upload--text"]').trigger("change", {
        target: { files: [file] },
      });
    });
  });

  it("allows a user to send a message to a group", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    cy.get('span').contains('test...').click();
    cy.get("div").should("contain", "hi");

    cy.get("textarea").type("Hello, how are you?");
    cy.contains("span", "Send").click();
    cy.get("div").should("contain", "Hello, how are you?");
  });

  it("send group image test", () => {
    cy.get('div[class="el-upload el-upload--text"]').click();
    cy.fixture("example.png", "base64").then((fileContent) => {
      const file = new File([fileContent], "example.png", {
        type: "image/png",
      });
      cy.get('div[class="el-upload el-upload--text"]').trigger("change", {
        target: { files: [file] },
      });
    });
  });

  it("find new firend test", () => {
    cy.contains("li", "Find Something New").click();
    cy.get("input").eq(1).type("one");
    cy.contains("span", "add new friend").click();

    cy.contains("span", "one").should("be.visible");
  });

  it("friend requests test", () => {
    cy.contains("li", "Friend Requests").click();
    cy.contains("span", "otha.mckenzie").should("be.visible");
  });

  it("group requests test", () => {
    cy.contains("li", "Group Invitations").click();
    cy.contains("span", "Rosenbaum-Toy").should("be.visible");
  });

  it("status list test", () => {
    cy.contains("li", "Status").click();
    cy.contains("div", "FAILED").should("be.visible");
  });

  it("create group test", () => {
    // cy.contains("li", "Find Something New").click();
    // cy.contains("span", "create new group").click();
    // cy.get("input[placeholder='Please Input Group Name Here']").type("test Group");
    // cy.contains("span", "Create Group").click();
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("log out")
      .click();
    cy.wait(3000);
    cy.get('input[name="username"]').type("tony");
    cy.get('input[name="password"]').type("123456");
    cy.get('button[type="submit"]').click();
    cy.get('button[type="submit"]').click();
    cy.get("img").should(
      "have.attr",
      "src",
      "https://s1.ax1x.com/2023/03/12/ppKvjns.png"
    );
  });

  it("friend hide test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    let temp = cy.get('#1634324162308231170');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("hide")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .should("not.be.visible");
  });

  it("friend show test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("show all friends")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .should("be.visible");
  });
  it("friend hide unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("keep hide friends")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .should("be.visible");
  });
  it("friend mute test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    let temp = cy.get('#1634324162308231170mute');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("mute")
      .click();
  });
  it("friend mute unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
  });
  it("group hide test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    let temp = cy.get('#1636504861510213634');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("hide")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .should("not.be.visible");
  });

  it("group show test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("show all groups")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .should("be.visible");
  });
  it("group hide unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("keep hide groups")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .should("be.visible");
  });
  it("group mute test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    let temp = cy.get('#1636504861510213634mute');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("mute")
      .click();
  });
  it("group mute unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
  });
  it("to personal information test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("edit my info")
      .click();
    cy.contains("span","change Avatar").should('be.visible');
  });

  it("change username test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(1).click();
    cy.get('input').eq(2).focus().type('stark');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'tonystark').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(1).click();
    cy.get('input').eq(2).focus().clear().type('tony');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'tony').should('be.visible');
  });

  it("change birthday test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(2).click();
    cy.get('input').eq(2).focus().clear().type('2000-01-01');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '2000-01-01').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(2).click();
    cy.get('input').eq(2).focus().clear().type('1999-09-09');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '1999-09-09').should('be.visible');
  });

  it("change phone test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(4).click();
    cy.get('input').eq(2).focus().clear().type('613-111-1111');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '613-111-1111').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(4).click();
    cy.get('input').eq(2).focus().clear().type('613-100-1000');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '613-100-1000').should('be.visible');
  });

  it("change email test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(5).click();
    cy.get('input').eq(2).focus().clear().type('example@gmail.com');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'example@gmail.com').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(5).click();
    cy.get('input').eq(2).focus().clear().type('ex@gmail.com');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'ex@gmail.com').should('be.visible');
  });

  it("change address test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(6).click();
    cy.get('input').eq(2).focus().clear().type('1234567');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '1234567').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(6).click();
    cy.get('input').eq(2).focus().clear().type('abcdefg');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'abcdefg').should('be.visible');
  });

  it("change password test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(3).click();
    cy.get('input[class="el-input__inner"]').eq(1).focus().clear().type("123456");
    cy.get('input[class="el-input__inner"]').eq(2).focus().clear().type("654321");
    cy.get('input[class="el-input__inner"]').eq(3).focus().clear().type("654321");
    cy.get('span').contains('Confirm').click();

    cy.get('button[class="el-button el-button--primary is-round"]').eq(3).click();
    cy.get('input[class="el-input__inner"]').eq(1).focus().clear().type("654321");
    cy.get('input[class="el-input__inner"]').eq(2).focus().clear().type("123456");
    cy.get('input[class="el-input__inner"]').eq(3).focus().clear().type("123456");
    cy.get('span').contains('Confirm').click();
  });
  it("Login Test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("log out")
      .click();
    cy.wait(3000);

    cy.get('input[name="username"]').type("tony");
    cy.get('input[name="password"]').type("123456");
    cy.get('button[type="submit"]').click();
    cy.get('button[type="submit"]').click();
    cy.get("img").should(
      "have.attr",
      "src",
      "https://s1.ax1x.com/2023/03/12/ppKvjns.png"
    );
  });
  it("allows a user to send a message to a friend", () => {
    cy.get('input[id="searchMyFriend"]').type("mike");
    cy.get('button[id="searchmf"]').click();
    cy.get("span").should("contain", "mike");

    cy.contains('span', 'Chat').click();
    cy.get("div").should("contain", "hi");

    cy.get("textarea").type("Hello, how are you?");
    cy.contains("span", "Send").click();
    cy.get("div").should("contain", "Hello, how are you?");
  });

  it("send image test", () => {
    cy.get('div[class="el-upload el-upload--text"]').click();
    cy.fixture("example.png", "base64").then((fileContent) => {
      const file = new File([fileContent], "example.png", {
        type: "image/png",
      });
      cy.get('div[class="el-upload el-upload--text"]').trigger("change", {
        target: { files: [file] },
      });
    });
  });

  it("allows a user to send a message to a group", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    cy.get('span').contains('test...').click();
    cy.get("div").should("contain", "hi");

    cy.get("textarea").type("Hello, how are you?");
    cy.contains("span", "Send").click();
    cy.get("div").should("contain", "Hello, how are you?");
  });

  it("send group image test", () => {
    cy.get('div[class="el-upload el-upload--text"]').click();
    cy.fixture("example.png", "base64").then((fileContent) => {
      const file = new File([fileContent], "example.png", {
        type: "image/png",
      });
      cy.get('div[class="el-upload el-upload--text"]').trigger("change", {
        target: { files: [file] },
      });
    });
  });

  it("find new firend test", () => {
    cy.contains("li", "Find Something New").click();
    cy.get("input").eq(1).type("one");
    cy.contains("span", "add new friend").click();

    cy.contains("span", "one").should("be.visible");
  });

  it("friend requests test", () => {
    cy.contains("li", "Friend Requests").click();
    cy.contains("span", "otha.mckenzie").should("be.visible");
  });

  it("group requests test", () => {
    cy.contains("li", "Group Invitations").click();
    cy.contains("span", "Rosenbaum-Toy").should("be.visible");
  });

  it("status list test", () => {
    cy.contains("li", "Status").click();
    cy.contains("div", "FAILED").should("be.visible");
  });

  it("create group test", () => {
    // cy.contains("li", "Find Something New").click();
    // cy.contains("span", "create new group").click();
    // cy.get("input[placeholder='Please Input Group Name Here']").type("test Group");
    // cy.contains("span", "Create Group").click();
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("log out")
      .click();
    cy.wait(3000);
    cy.get('input[name="username"]').type("tony");
    cy.get('input[name="password"]').type("123456");
    cy.get('button[type="submit"]').click();
    cy.get('button[type="submit"]').click();
    cy.get("img").should(
      "have.attr",
      "src",
      "https://s1.ax1x.com/2023/03/12/ppKvjns.png"
    );
  });

  it("friend hide test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    let temp = cy.get('#1634324162308231170');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("hide")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .should("not.be.visible");
  });

  it("friend show test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("show all friends")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .should("be.visible");
  });
  it("friend hide unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("keep hide friends")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .should("be.visible");
  });
  it("friend mute test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    let temp = cy.get('#1634324162308231170mute');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("mute")
      .click();
  });
  it("friend mute unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
  });
  it("group hide test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    let temp = cy.get('#1636504861510213634');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("hide")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .should("not.be.visible");
  });

  it("group show test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("show all groups")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .should("be.visible");
  });
  it("group hide unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("keep hide groups")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .should("be.visible");
  });
  it("group mute test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    let temp = cy.get('#1636504861510213634mute');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("mute")
      .click();
  });
  it("group mute unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
  });
  it("to personal information test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("edit my info")
      .click();
    cy.contains("span","change Avatar").should('be.visible');
  });

  it("change username test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(1).click();
    cy.get('input').eq(2).focus().type('stark');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'tonystark').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(1).click();
    cy.get('input').eq(2).focus().clear().type('tony');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'tony').should('be.visible');
  });

  it("change birthday test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(2).click();
    cy.get('input').eq(2).focus().clear().type('2000-01-01');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '2000-01-01').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(2).click();
    cy.get('input').eq(2).focus().clear().type('1999-09-09');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '1999-09-09').should('be.visible');
  });

  it("change phone test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(4).click();
    cy.get('input').eq(2).focus().clear().type('613-111-1111');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '613-111-1111').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(4).click();
    cy.get('input').eq(2).focus().clear().type('613-100-1000');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '613-100-1000').should('be.visible');
  });

  it("change email test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(5).click();
    cy.get('input').eq(2).focus().clear().type('example@gmail.com');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'example@gmail.com').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(5).click();
    cy.get('input').eq(2).focus().clear().type('ex@gmail.com');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'ex@gmail.com').should('be.visible');
  });

  it("change address test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(6).click();
    cy.get('input').eq(2).focus().clear().type('1234567');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '1234567').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(6).click();
    cy.get('input').eq(2).focus().clear().type('abcdefg');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'abcdefg').should('be.visible');
  });

  it("change password test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(3).click();
    cy.get('input[class="el-input__inner"]').eq(1).focus().clear().type("123456");
    cy.get('input[class="el-input__inner"]').eq(2).focus().clear().type("654321");
    cy.get('input[class="el-input__inner"]').eq(3).focus().clear().type("654321");
    cy.get('span').contains('Confirm').click();

    cy.get('button[class="el-button el-button--primary is-round"]').eq(3).click();
    cy.get('input[class="el-input__inner"]').eq(1).focus().clear().type("654321");
    cy.get('input[class="el-input__inner"]').eq(2).focus().clear().type("123456");
    cy.get('input[class="el-input__inner"]').eq(3).focus().clear().type("123456");
    cy.get('span').contains('Confirm').click();
  });
  it("Login Test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("log out")
      .click();
    cy.wait(3000);

    cy.get('input[name="username"]').type("tony");
    cy.get('input[name="password"]').type("123456");
    cy.get('button[type="submit"]').click();
    cy.get('button[type="submit"]').click();
    cy.get("img").should(
      "have.attr",
      "src",
      "https://s1.ax1x.com/2023/03/12/ppKvjns.png"
    );
  });
  it("allows a user to send a message to a friend", () => {
    cy.get('input[id="searchMyFriend"]').type("mike");
    cy.get('button[id="searchmf"]').click();
    cy.get("span").should("contain", "mike");

    cy.contains('span', 'Chat').click();
    cy.get("div").should("contain", "hi");

    cy.get("textarea").type("Hello, how are you?");
    cy.contains("span", "Send").click();
    cy.get("div").should("contain", "Hello, how are you?");
  });

  it("send image test", () => {
    cy.get('div[class="el-upload el-upload--text"]').click();
    cy.fixture("example.png", "base64").then((fileContent) => {
      const file = new File([fileContent], "example.png", {
        type: "image/png",
      });
      cy.get('div[class="el-upload el-upload--text"]').trigger("change", {
        target: { files: [file] },
      });
    });
  });

  it("allows a user to send a message to a group", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    cy.get('span').contains('test...').click();
    cy.get("div").should("contain", "hi");

    cy.get("textarea").type("Hello, how are you?");
    cy.contains("span", "Send").click();
    cy.get("div").should("contain", "Hello, how are you?");
  });

  it("send group image test", () => {
    cy.get('div[class="el-upload el-upload--text"]').click();
    cy.fixture("example.png", "base64").then((fileContent) => {
      const file = new File([fileContent], "example.png", {
        type: "image/png",
      });
      cy.get('div[class="el-upload el-upload--text"]').trigger("change", {
        target: { files: [file] },
      });
    });
  });

  it("find new firend test", () => {
    cy.contains("li", "Find Something New").click();
    cy.get("input").eq(1).type("one");
    cy.contains("span", "add new friend").click();

    cy.contains("span", "one").should("be.visible");
  });

  it("friend requests test", () => {
    cy.contains("li", "Friend Requests").click();
    cy.contains("span", "otha.mckenzie").should("be.visible");
  });

  it("group requests test", () => {
    cy.contains("li", "Group Invitations").click();
    cy.contains("span", "Rosenbaum-Toy").should("be.visible");
  });

  it("status list test", () => {
    cy.contains("li", "Status").click();
    cy.contains("div", "FAILED").should("be.visible");
  });

  it("create group test", () => {
    // cy.contains("li", "Find Something New").click();
    // cy.contains("span", "create new group").click();
    // cy.get("input[placeholder='Please Input Group Name Here']").type("test Group");
    // cy.contains("span", "Create Group").click();
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("log out")
      .click();
    cy.wait(3000);
    cy.get('input[name="username"]').type("tony");
    cy.get('input[name="password"]').type("123456");
    cy.get('button[type="submit"]').click();
    cy.get('button[type="submit"]').click();
    cy.get("img").should(
      "have.attr",
      "src",
      "https://s1.ax1x.com/2023/03/12/ppKvjns.png"
    );
  });

  it("friend hide test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    let temp = cy.get('#1634324162308231170');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("hide")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .should("not.be.visible");
  });

  it("friend show test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("show all friends")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .should("be.visible");
  });
  it("friend hide unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("keep hide friends")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .should("be.visible");
  });
  it("friend mute test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    let temp = cy.get('#1634324162308231170mute');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("mute")
      .click();
  });
  it("friend mute unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppaUR74.png"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
  });
  it("group hide test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    let temp = cy.get('#1636504861510213634');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("hide")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .should("not.be.visible");
  });

  it("group show test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("show all groups")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .should("be.visible");
  });
  it("group hide unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("keep hide groups")
      .click();
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .should("be.visible");
  });
  it("group mute test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    let temp = cy.get('#1636504861510213634mute');//get('span:not([type="submit"])');
    console.log(temp);
    temp.should("be.visible")
      .contains("mute")
      .click();
  });
  it("group mute unset test", () => {
    cy.get('img[src="https://s1.ax1x.com/2023/03/22/ppad6wF.jpg"]')
      .eq(0)
      .click();
    cy.get('span:not([type="submit"])')
      .should("be.visible")
      .contains("unset")
      .click();
  });
  it("to personal information test", () => {
    cy.get(
      'img[src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"]'
    )
      .eq(0)
      .click();
    cy.get('li:not([type="submit"])')
      .should("be.visible")
      .contains("edit my info")
      .click();
    cy.contains("span","change Avatar").should('be.visible');
  });

  it("change username test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(1).click();
    cy.get('input').eq(2).focus().type('stark');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'tonystark').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(1).click();
    cy.get('input').eq(2).focus().clear().type('tony');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'tony').should('be.visible');
  });

  it("change birthday test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(2).click();
    cy.get('input').eq(2).focus().clear().type('2000-01-01');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '2000-01-01').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(2).click();
    cy.get('input').eq(2).focus().clear().type('1999-09-09');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '1999-09-09').should('be.visible');
  });

  it("change phone test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(4).click();
    cy.get('input').eq(2).focus().clear().type('613-111-1111');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '613-111-1111').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(4).click();
    cy.get('input').eq(2).focus().clear().type('613-100-1000');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '613-100-1000').should('be.visible');
  });

  it("change email test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(5).click();
    cy.get('input').eq(2).focus().clear().type('example@gmail.com');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'example@gmail.com').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(5).click();
    cy.get('input').eq(2).focus().clear().type('ex@gmail.com');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'ex@gmail.com').should('be.visible');
  });

  it("change address test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(6).click();
    cy.get('input').eq(2).focus().clear().type('1234567');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', '1234567').should('be.visible');

    cy.get('button[class="el-button el-button--primary is-round"]').eq(6).click();
    cy.get('input').eq(2).focus().clear().type('abcdefg');
    cy.get('input').eq(2).type('{enter}');
    cy.contains('span', 'abcdefg').should('be.visible');
  });

  it("change password test", () => {
    cy.get('button[class="el-button el-button--primary is-round"]').eq(3).click();
    cy.get('input[class="el-input__inner"]').eq(1).focus().clear().type("123456");
    cy.get('input[class="el-input__inner"]').eq(2).focus().clear().type("654321");
    cy.get('input[class="el-input__inner"]').eq(3).focus().clear().type("654321");
    cy.get('span').contains('Confirm').click();

    cy.get('button[class="el-button el-button--primary is-round"]').eq(3).click();
    cy.get('input[class="el-input__inner"]').eq(1).focus().clear().type("654321");
    cy.get('input[class="el-input__inner"]').eq(2).focus().clear().type("123456");
    cy.get('input[class="el-input__inner"]').eq(3).focus().clear().type("123456");
    cy.get('span').contains('Confirm').click();
  })
});
