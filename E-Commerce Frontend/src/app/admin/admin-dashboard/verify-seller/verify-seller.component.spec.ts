import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifySellerComponent } from './verify-seller.component';

describe('VerifySellerComponent', () => {
  let component: VerifySellerComponent;
  let fixture: ComponentFixture<VerifySellerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerifySellerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VerifySellerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
